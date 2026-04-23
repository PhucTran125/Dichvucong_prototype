package com.viettel.docsearch.service;

import com.viettel.docsearch.domain.OAuthAuthCode;
import com.viettel.docsearch.domain.User;
import com.viettel.docsearch.repo.OAuthAuthCodeRepository;
import com.viettel.docsearch.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class MockVneidService {

    private static final Duration CODE_TTL = Duration.ofMinutes(5);
    private static final SecureRandom RNG = new SecureRandom();

    private final UserRepository users;
    private final OAuthAuthCodeRepository codes;

    public MockVneidService(UserRepository users, OAuthAuthCodeRepository codes) {
        this.users = users;
        this.codes = codes;
    }

    @Transactional(readOnly = true)
    public List<User> listIdentities() {
        return users.findAll();
    }

    @Transactional
    public String issueCode(UUID userId, String redirectUri, String state) {
        User u = users.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Unknown user: " + userId));
        byte[] buf = new byte[32];
        RNG.nextBytes(buf);
        String code = Base64.getUrlEncoder().withoutPadding().encodeToString(buf);
        codes.save(new OAuthAuthCode(code, u, redirectUri, state, Instant.now().plus(CODE_TTL)));
        return code;
    }

    @Transactional
    public User consumeCode(String code) {
        OAuthAuthCode ac = codes.findById(code)
            .orElseThrow(() -> new IllegalArgumentException("Invalid code"));
        if (ac.isConsumed()) throw new IllegalArgumentException("Code already used");
        if (ac.getExpiresAt().isBefore(Instant.now())) throw new IllegalArgumentException("Code expired");
        ac.markConsumed();
        codes.save(ac);
        return ac.getUser();
    }
}
