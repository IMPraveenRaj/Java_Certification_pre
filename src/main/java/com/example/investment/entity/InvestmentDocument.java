package com.example.investment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "INVESTMENT_DOCUMENT", schema = "NVB_DOCS_OWNER")
@Data
public class InvestmentDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "investment_document_seq")
    @SequenceGenerator(
            name = "investment_document_seq",
            sequenceName = "NVB_DOCS_OWNER.INVESTMENT_DOCUMENT_SEQ",
            allocationSize = 1
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "UUID", nullable = false, length = 36)
    private String uuid;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "PRODUCT_TYPE", nullable = false, length = 30)
    private String productType;

    @Column(name = "PROCESS_TYPE", nullable = false, length = 30)
    private String processType;

    @Column(name = "CUSTOMER", nullable = false, length = 128)
    private String customer;

    @Column(name = "EMPLOYEE", length = 10)
    private String employee;

    @Column(name = "AGREEMENT_ID", length = 40)
    private String agreementId;

    @Column(name = "EXTERNAL_ID", nullable = false, length = 36)
    private String externalId;

    @Column(name = "TYPE", nullable = false, length = 30)
    private String type;

    @Column(name = "STATUS", nullable = false, length = 12)
    private String status;

    @Column(name = "SIGNING_STATUS", nullable = false, length = 12)
    private String signingStatus;

    @Column(name = "DOCUMENT_CONTENT_ID")
    private Long documentContentId;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_ON", nullable = false)
    private LocalDateTime updatedOn;

    @Column(name = "EXPIRES_ON")
    private LocalDateTime expiresOn;

    @Column(name = "PARENT_DOSSIER_ID")
    private Long parentDossierId;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedOn == null) {
            updatedOn = now;
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedOn = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSigningStatus() {
        return signingStatus;
    }

    public void setSigningStatus(String signingStatus) {
        this.signingStatus = signingStatus;
    }

    public Long getDocumentContentId() {
        return documentContentId;
    }

    public void setDocumentContentId(Long documentContentId) {
        this.documentContentId = documentContentId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public LocalDateTime getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(LocalDateTime expiresOn) {
        this.expiresOn = expiresOn;
    }

    public Long getParentDossierId() {
        return parentDossierId;
    }

    public void setParentDossierId(Long parentDossierId) {
        this.parentDossierId = parentDossierId;
    }
}
