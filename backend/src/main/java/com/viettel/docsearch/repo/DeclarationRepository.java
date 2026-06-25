package com.viettel.docsearch.repo;

import com.viettel.docsearch.domain.Declaration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Maps the Oracle DECLARATION table (LLTP). Available under the 'oracle' profile,
 * where the table and DECLARATION_SEQ exist in the DBDaotao schema.
 */
public interface DeclarationRepository extends JpaRepository<Declaration, Long> {

    List<Declaration> findByIdentifyNo(String identifyNo);

    List<Declaration> findByJusticeNo(String justiceNo);

    List<Declaration> findByFullNameContainingIgnoreCase(String fullName);

    List<Declaration> findByReceiveNo(String receiveNo);
}
