package com.viettel.docsearch.web;

import com.viettel.docsearch.domain.User;
import com.viettel.docsearch.service.CurrentUserService;
import com.viettel.docsearch.service.JwtService;
import com.viettel.docsearch.service.MockVneidService;
import com.viettel.docsearch.service.PasswordAuthService;
import com.viettel.docsearch.web.dto.ErrorResponse;
import com.viettel.docsearch.web.dto.PasswordLoginRequest;
import com.viettel.docsearch.web.dto.TokenRequest;
import com.viettel.docsearch.web.dto.TokenResponse;
import com.viettel.docsearch.web.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final MockVneidService vneid;
    private final PasswordAuthService passwordAuth;
    private final JwtService jwt;
    private final UserMapper userMapper;
    private final CurrentUserService currentUser;

    public AuthController(MockVneidService vneid,
                          PasswordAuthService passwordAuth,
                          JwtService jwt,
                          UserMapper userMapper,
                          CurrentUserService currentUser) {
        this.vneid = vneid;
        this.passwordAuth = passwordAuth;
        this.jwt = jwt;
        this.userMapper = userMapper;
        this.currentUser = currentUser;
    }

    @PostMapping("/password-login")
    public ResponseEntity<?> passwordLogin(@Valid @RequestBody PasswordLoginRequest req) {
        try {
            User user = passwordAuth.authenticate(req.citizenId().trim(), req.password());
            String token = jwt.issue(user.getId());
            return ResponseEntity.ok(new TokenResponse(token, jwt.ttlSeconds(), userMapper.toDto(user)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(new ErrorResponse("INVALID_CREDENTIALS", e.getMessage()));
        }
    }

    @PostMapping("/mock-vneid/token")
    public ResponseEntity<?> token(@Valid @RequestBody TokenRequest req) {
        try {
            User user = vneid.consumeCode(req.code());
            String token = jwt.issue(user.getId());
            return ResponseEntity.ok(new TokenResponse(token, jwt.ttlSeconds(), userMapper.toDto(user)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new ErrorResponse("INVALID_CODE", e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        User u = currentUser.current().orElse(null);
        if (u == null) {
            return ResponseEntity.status(401).body(new ErrorResponse("UNAUTHENTICATED", "No valid token"));
        }
        return ResponseEntity.ok(userMapper.toDto(u));
    }
}
