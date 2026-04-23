package com.viettel.docsearch.web.dto;

import java.util.UUID;

public record AttachmentDto(
    UUID id,
    String fileName,
    String mimeType,
    long sizeBytes
) {}
