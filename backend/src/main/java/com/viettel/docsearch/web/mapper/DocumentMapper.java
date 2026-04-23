package com.viettel.docsearch.web.mapper;

import com.viettel.docsearch.domain.Document;
import com.viettel.docsearch.domain.DocumentAttachment;
import com.viettel.docsearch.web.dto.AttachmentDto;
import com.viettel.docsearch.web.dto.DocumentFullDto;
import com.viettel.docsearch.web.dto.DocumentPreviewDto;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {

    public DocumentPreviewDto toPreview(Document d) {
        return new DocumentPreviewDto(
            d.getId(),
            d.getType(),
            d.getType().getVietnameseLabel(),
            d.getTitle(),
            d.getDocumentNumber(),
            d.getIssuedAt(),
            d.getIssuingAuthority(),
            d.getStatus(),
            NameMasker.mask(d.getSubjectFullName()),
            d.getSubjectCitizenId(),
            true
        );
    }

    public DocumentFullDto toFull(Document d) {
        return new DocumentFullDto(
            d.getId(),
            d.getType(),
            d.getType().getVietnameseLabel(),
            d.getTitle(),
            d.getDocumentNumber(),
            d.getIssuedAt(),
            d.getIssuingAuthority(),
            d.getStatus(),
            d.getSubjectFullName(),
            d.getSubjectDob(),
            d.getSubjectCitizenId(),
            d.getSubjectAddress(),
            d.getBody(),
            d.getAttachments().stream().map(this::toAttachmentDto).toList(),
            false
        );
    }

    private AttachmentDto toAttachmentDto(DocumentAttachment a) {
        return new AttachmentDto(a.getId(), a.getFileName(), a.getMimeType(), a.getSizeBytes());
    }
}
