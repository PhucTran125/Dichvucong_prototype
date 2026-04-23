package com.viettel.docsearch.web.dto;

import com.viettel.docsearch.domain.DocumentStatus;
import com.viettel.docsearch.domain.DocumentType;

import java.time.LocalDate;
import java.util.UUID;

public record DocumentPreviewDto(
    UUID id,
    DocumentType type,
    String typeLabel,
    String title,
    String documentNumber,
    LocalDate issuedAt,
    String issuingAuthority,
    DocumentStatus status,
    String subjectFullNameMasked,
    String subjectCitizenId,
    boolean preview
) {}
