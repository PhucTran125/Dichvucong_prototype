package com.viettel.docsearch.web.dto;

import com.viettel.docsearch.domain.DocumentStatus;
import com.viettel.docsearch.domain.DocumentType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record DocumentFullDto(
    UUID id,
    DocumentType type,
    String typeLabel,
    String title,
    String documentNumber,
    LocalDate issuedAt,
    String issuingAuthority,
    DocumentStatus status,
    String subjectFullName,
    LocalDate subjectDob,
    String subjectCitizenId,
    String subjectAddress,
    String body,
    List<AttachmentDto> attachments,
    boolean preview
) {}
