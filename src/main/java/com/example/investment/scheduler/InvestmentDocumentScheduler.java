package com.example.investment.scheduler;

import com.example.investment.entity.InvestmentDocument;
import com.example.investment.repository.InvestmentDocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

@Service
public class InvestmentDocumentScheduler {

    private static final Logger log = LoggerFactory.getLogger(InvestmentDocumentScheduler.class);

    private final InvestmentDocumentRepository repository;

    public InvestmentDocumentScheduler(InvestmentDocumentRepository repository) {
        this.repository = repository;
    }

    /**
     * Scheduler #1 (existing):
     * Uses a single platform thread and one transaction to insert 1000 rows every 5 minutes.
     */
    @Scheduled(fixedRateString = "PT5M")
    @Transactional
    public void insertBatch() {
        String threadName = Thread.currentThread().getName();
        log.info("[Platform Thread] Scheduler triggered on thread: '{}'", threadName);

        LocalDateTime now = LocalDateTime.now();
        log.info("[Platform Thread] Starting batch insert of 1000 investment documents at {}", now);

        List<InvestmentDocument> batch = new ArrayList<>(1000);

        for (int i = 0; i < 1000; i++) {
            InvestmentDocument doc = buildDocument(i, now);
            batch.add(doc);
        }

        repository.saveAll(batch);

        log.info("[classic] Finished inserting 1000 investment documents at {}", LocalDateTime.now());
    }

    /**
     * Scheduler #2 (new):
     * Also runs every 5 minutes, but uses virtual threads to do the inserts.
     * - The scheduled method itself still runs on Spring's scheduler thread.
     * - Inside it we create a virtual-thread-per-task executor.
     * - We split 1000 inserts into 10 tasks * 100 documents each.
     * - Each task runs on its own virtual thread and uses repository.save(...)
     * we are truing to understand what exactly happening heer to understand the internals
     *
     *
     */


    @Scheduled(fixedRateString = "PT5M", initialDelayString = "PT1M")
    public void insertBatchWithVirtualThreads() {
        log.info("[vt] Scheduler running on thread: {}", Thread.currentThread());

        int totalDocumentsToInsert = 1000;
        int documentsPerTask = 100;
        int totalTasks = totalDocumentsToInsert / documentsPerTask;

        LocalDateTime batchTimestamp = LocalDateTime.now();
        log.info("[vt] Submitting {} virtual-thread tasks ({} documents per task)",
                totalTasks, documentsPerTask);

        // Java 21: virtual-thread-per-task executor
        try (var virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor()) {

            for (int taskNumber = 0; taskNumber < totalTasks; taskNumber++) {
                final int currentTaskNumber = taskNumber;

                virtualThreadExecutor.submit(() -> {
                    Thread currentThread = Thread.currentThread();
                    log.info("[vt] Task {} started on {}", currentTaskNumber, currentThread);

                    for (int documentOffset = 0; documentOffset < documentsPerTask; documentOffset++) {
                        int globalDocumentIndex =
                                currentTaskNumber * documentsPerTask + documentOffset;

                        InvestmentDocument document =
                                buildDocument(globalDocumentIndex, batchTimestamp);

                        // Each save() runs in its own small DB transaction
                        repository.save(document);
                    }

                    log.info("[vt] Task {} finished on {}", currentTaskNumber, currentThread);
                });
            }

            // executor.close() blocks until all virtual-thread tasks complete
        } catch (Exception ex) {
            log.error("[vt] Error while executing virtual-thread batch insert", ex);
        }

        log.info("[vt] All virtual-thread tasks completed at {}", LocalDateTime.now());
    }


    /**
     * Helper to build an InvestmentDocument with dummy data.
     */
    private InvestmentDocument buildDocument(int i, LocalDateTime now) {
        InvestmentDocument doc = new InvestmentDocument();
        doc.setUuid(UUID.randomUUID().toString());
        doc.setName("Scheduled document " + i);
        doc.setProductType("PRODUCT_" + (i % 5));
        doc.setProcessType("PROCESS_" + (i % 3));
        doc.setCustomer("CUSTOMER_" + (i % 100));
        doc.setEmployee("EMP" + (i % 50));
        doc.setAgreementId("AGR-" + i);
        doc.setExternalId(UUID.randomUUID().toString());
        doc.setType("TYPE_A");
        doc.setStatus("ACTIVE");
        doc.setSigningStatus("PENDING");
        doc.setCreatedAt(now);
        doc.setUpdatedOn(now);
        return doc;
    }
}
