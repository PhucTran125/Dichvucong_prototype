package com.viettel.docsearch.repo;

import com.viettel.docsearch.domain.SynthesisVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Maps the Oracle SYNTHESIS_VERIFICATION table (LLTP). Available under the 'oracle'
 * profile against the DBDaotao schema, and against the H2 demo by default.
 */
public interface SynthesisVerificationRepository extends JpaRepository<SynthesisVerification, Long> {

    List<SynthesisVerification> findByDocumentNo(String documentNo);

    // The entity's @ManyToOne field is named declarationId (type Declaration),
    // so the nested DECLARATION_ID FK is traversed as declarationId.declarationId.
    List<SynthesisVerification> findByDeclarationId_DeclarationId(Long declarationId);

    /** The active issued Phiếu for a declaration. */
    List<SynthesisVerification> findByDeclarationId_DeclarationIdAndIsActive(Long declarationId, String isActive);
}
