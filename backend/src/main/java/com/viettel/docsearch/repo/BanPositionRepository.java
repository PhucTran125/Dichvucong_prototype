package com.viettel.docsearch.repo;

import com.viettel.docsearch.domain.BanPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Maps the Oracle BAN_POSITION table (cấm đảm nhiệm chức vụ of a declaration). */
public interface BanPositionRepository extends JpaRepository<BanPosition, Long> {

    List<BanPosition> findByDeclarationId_DeclarationIdAndIsActive(Long declarationId, String isActive);
}
