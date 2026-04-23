package com.viettel.docsearch.repo;

import com.viettel.docsearch.domain.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DocumentRepository extends JpaRepository<Document, UUID> {

    @Query("SELECT d FROM Document d WHERE d.searchText LIKE CONCAT('%', :q, '%') ORDER BY d.issuedAt DESC")
    Page<Document> searchByFoldedText(@Param("q") String foldedQuery, Pageable pageable);

    @Query("SELECT d FROM Document d LEFT JOIN FETCH d.owner LEFT JOIN FETCH d.attachments WHERE d.id = :id")
    Optional<Document> findFullById(@Param("id") UUID id);
}
