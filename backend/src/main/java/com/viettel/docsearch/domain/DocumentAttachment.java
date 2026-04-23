package com.viettel.docsearch.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "document_attachment")
public class DocumentAttachment {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Column(name = "file_name", nullable = false, length = 256)
    private String fileName;

    @Column(name = "mime_type", nullable = false, length = 64)
    private String mimeType;

    @Column(name = "size_bytes", nullable = false)
    private long sizeBytes;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    protected DocumentAttachment() {}

    public UUID getId() { return id; }
    public Document getDocument() { return document; }
    public String getFileName() { return fileName; }
    public String getMimeType() { return mimeType; }
    public long getSizeBytes() { return sizeBytes; }
    public int getSortOrder() { return sortOrder; }
}
