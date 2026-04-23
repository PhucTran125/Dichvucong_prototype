package com.viettel.docsearch.service;

import com.viettel.docsearch.domain.User;
import com.viettel.docsearch.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordAuthService {

    // Prototype-only: every seeded user authenticates with the same password.
    private static final String PROTOTYPE_PASSWORD = "admin123";

    private final UserRepository users;

    public PasswordAuthService(UserRepository users) {
        this.users = users;
    }

    @Transactional(readOnly = true)
    public User authenticate(String citizenId, String password) {
        User user = users.findByCitizenId(citizenId).orElse(null);
        if (user == null || !PROTOTYPE_PASSWORD.equals(password)) {
            throw new IllegalArgumentException("Số CCCD hoặc mật khẩu không đúng");
        }
        return user;
    }
}
