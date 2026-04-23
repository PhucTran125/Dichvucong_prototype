package com.viettel.docsearch.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "document")
public class Document {

    @Id
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false, unique = true)
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 32)
    private DocumentType type;

    @Column(name = "title", nullable = false, length = 256)
    private String title;

    @Column(name = "document_number", nullable = false, unique = true, length = 64)
    private String documentNumber;

    @Column(name = "issued_at", nullable = false)
    private LocalDate issuedAt;

    @Column(name = "issuing_authority", nullable = false, length = 256)
    private String issuingAuthority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 16)
    private DocumentStatus status;

    @Column(name = "subject_full_name", nullable = false, length = 128)
    private String subjectFullName;

    @Column(name = "subject_dob", nullable = false)
    private LocalDate subjectDob;

    @Column(name = "subject_citizen_id", nullable = false, length = 20)
    private String subjectCitizenId;

    @Column(name = "subject_address", nullable = false, length = 256)
    private String subjectAddress;

    @Column(name = "body", nullable = false, length = 8000)
    private String body;

    @Column(name = "search_text", nullable = false, length = 1024)
    private String searchText;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany(mappedBy = "document", fetch = FetchType.LAZY)
    @OrderBy("sortOrder ASC")
    private List<DocumentAttachment> attachments = new ArrayList<>();

    protected Document() {}

    public UUID getId() { return id; }
    public User getOwner() { return owner; }
    public DocumentType getType() { return type; }
    public String getTitle() { return title; }
    public String getDocumentNumber() { return documentNumber; }
    public LocalDate getIssuedAt() { return issuedAt; }
    public String getIssuingAuthority() { return issuingAuthority; }
    public DocumentStatus getStatus() { return status; }
    public String getSubjectFullName() { return subjectFullName; }
    public LocalDate getSubjectDob() { return subjectDob; }
    public String getSubjectCitizenId() { return subjectCitizenId; }
    public String getSubjectAddress() { return subjectAddress; }
    public String getBody() { return body; }
    public String getSearchText() { return searchText; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public List<DocumentAttachment> getAttachments() { return attachments; }
}
