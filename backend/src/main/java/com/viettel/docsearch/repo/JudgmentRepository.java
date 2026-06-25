package com.viettel.docsearch.repo;

import com.viettel.docsearch.domain.Judgment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Maps the Oracle JUDGMENT table (LLTP án tích — court judgments of a declaration). */
public interface JudgmentRepository extends JpaRepository<Judgment, Long> {

    List<Judgment> findByDeclarationId_DeclarationIdAndIsActive(Long declarationId, String isActive);
}
