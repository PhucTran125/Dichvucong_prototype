package com.viettel.docsearch.repo;

import com.viettel.docsearch.domain.OAuthAuthCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthAuthCodeRepository extends JpaRepository<OAuthAuthCode, String> {
}
