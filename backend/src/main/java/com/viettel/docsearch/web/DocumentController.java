package com.viettel.docsearch.web;

import com.viettel.docsearch.domain.Document;
import com.viettel.docsearch.domain.User;
import com.viettel.docsearch.service.CurrentUserService;
import com.viettel.docsearch.service.DocumentService;
import com.viettel.docsearch.service.QrService;
import com.viettel.docsearch.web.dto.DocumentPreviewDto;
import com.viettel.docsearch.web.dto.ErrorResponse;
import com.viettel.docsearch.web.dto.PageResponse;
import com.viettel.docsearch.web.mapper.DocumentMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentService docs;
    private final DocumentMapper mapper;
    private final QrService qr;
    private final CurrentUserService currentUser;

    public DocumentController(DocumentService docs, DocumentMapper mapper, QrService qr, CurrentUserService currentUser) {
        this.docs = docs;
        this.mapper = mapper;
        this.qr = qr;
        this.currentUser = currentUser;
    }

    @GetMapping("/search")
    public PageResponse<DocumentPreviewDto> search(
            @RequestParam(name = "q", required = false, defaultValue = "") String q,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size) {
        int capped = Math.min(Math.max(size, 1), 50);
        Page<Document> p = docs.search(q, Math.max(page, 0), capped);
        return new PageResponse<>(
            p.getContent().stream().map(mapper::toPreview).toList(),
            p.getNumber(),
            p.getSize(),
            p.getTotalElements(),
            p.getTotalPages()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        Optional<Document> found = docs.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.status(404).body(new ErrorResponse("NOT_FOUND", "Document not found"));
        }
        Document doc = found.get();
        User user = currentUser.current().orElse(null);
        if (docs.canViewFull(user, doc)) {
            return ResponseEntity.ok(mapper.toFull(doc));
        }
        return ResponseEntity.ok(mapper.toPreview(doc));
    }

    @GetMapping(value = "/{id}/qr.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> qrPng(@PathVariable UUID id) {
        if (docs.findById(id).isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        byte[] png = qr.pngFor(id);
        return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(png);
    }
}
