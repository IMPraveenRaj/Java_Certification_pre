package com.example.investment.repository;

import com.example.investment.entity.InvestmentDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvestmentDocumentRepository extends JpaRepository<InvestmentDocument, Long> {
}
