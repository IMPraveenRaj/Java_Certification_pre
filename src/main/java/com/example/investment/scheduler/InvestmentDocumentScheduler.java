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

@Service
public class InvestmentDocumentScheduler {

    private static final Logger log = LoggerFactory.getLogger(InvestmentDocumentScheduler.class);

    private final InvestmentDocumentRepository repository;

    public InvestmentDocumentScheduler(InvestmentDocumentRepository repository) {
        this.repository = repository;
    }

    //Runs once every 5 minutes
    @Scheduled(fixedRateString = "PT5M")
    @Transactional
    public void insertBatch() {
        String threadName = Thread.currentThread().getName();
        log.info("Scheduler triggered on thread: {}", threadName);

        LocalDateTime now = LocalDateTime.now();
        log.info("Starting batch insert of 1000 investment documents at {}", now);

        List<InvestmentDocument> batch = new ArrayList<>(1000);

        for (int i = 0; i < 1000; i++) {
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
            batch.add(doc);
        }

        repository.saveAll(batch);

        log.info("Finished inserting 1000 investment documents at {}", LocalDateTime.now());
    }
}
