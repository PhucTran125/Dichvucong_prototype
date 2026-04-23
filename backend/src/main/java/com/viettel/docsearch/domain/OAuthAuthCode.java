package com.viettel.docsearch.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "oauth_auth_code")
public class OAuthAuthCode {

    @Id
    @Column(name = "code", length = 64)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "redirect_uri", nullable = false, length = 512)
    private String redirectUri;

    @Column(name = "state", length = 128)
    private String state;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "consumed", nullable = false)
    private boolean consumed;

    protected OAuthAuthCode() {}

    public OAuthAuthCode(String code, User user, String redirectUri, String state, Instant expiresAt) {
        this.code = code;
        this.user = user;
        this.redirectUri = redirectUri;
        this.state = state;
        this.expiresAt = expiresAt;
        this.consumed = false;
    }

    public String getCode() { return code; }
    public User getUser() { return user; }
    public String getRedirectUri() { return redirectUri; }
    public String getState() { return state; }
    public Instant getExpiresAt() { return expiresAt; }
    public boolean isConsumed() { return consumed; }

    public void markConsumed() { this.consumed = true; }
}
