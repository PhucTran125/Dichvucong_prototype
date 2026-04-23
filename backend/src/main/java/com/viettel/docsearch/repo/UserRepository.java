package com.viettel.docsearch.repo;

import com.viettel.docsearch.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByVneidSubject(String vneidSubject);
    Optional<User> findByCitizenId(String citizenId);
}
