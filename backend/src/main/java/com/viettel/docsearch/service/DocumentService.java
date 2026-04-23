package com.viettel.docsearch.service;

import com.viettel.docsearch.domain.Document;
import com.viettel.docsearch.domain.User;
import com.viettel.docsearch.repo.DocumentRepository;
import com.viettel.docsearch.search.SearchTextFolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {

    private final DocumentRepository repo;

    public DocumentService(DocumentRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public Page<Document> search(String q, int page, int size) {
        String folded = SearchTextFolder.fold(q == null ? "" : q);
        return repo.searchByFoldedText(folded, PageRequest.of(page, size));
    }

    @Transactional(readOnly = true)
    public Optional<Document> findById(UUID id) {
        return repo.findFullById(id);
    }

    public boolean canViewFull(User currentUser, Document doc) {
        return currentUser != null
            && doc.getOwner() != null
            && doc.getOwner().getId().equals(currentUser.getId());
    }
}
