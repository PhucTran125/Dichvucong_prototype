package com.viettel.docsearch.repo;

import com.viettel.docsearch.domain.DocumentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocumentAttachmentRepository extends JpaRepository<DocumentAttachment, UUID> {
}
