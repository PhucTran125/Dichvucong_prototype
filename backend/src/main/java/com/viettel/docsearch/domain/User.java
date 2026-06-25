package com.viettel.docsearch.domain;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    private UUID id;

    @Column(name = "vneid_subject", nullable = false, unique = true, length = 64)
    private String vneidSubject;

    @Column(name = "full_name", nullable = false, length = 128)
    private String fullName;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "citizen_id", nullable = false, unique = true, length = 20)
    private String citizenId;

    @Column(name = "address", nullable = false, length = 256)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 16)
    private AccountType accountType = AccountType.CITIZEN;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    protected User() {}

    public UUID getId() { return id; }
    public String getVneidSubject() { return vneidSubject; }
    public String getFullName() { return fullName; }
    public LocalDate getDob() { return dob; }
    public String getCitizenId() { return citizenId; }
    public String getAddress() { return address; }
    public AccountType getAccountType() { return accountType; }
    public boolean isOrganization() { return accountType == AccountType.ORG; }
    public boolean isOfficer() { return accountType == AccountType.OFFICER; }
    public Instant getCreatedAt() { return createdAt; }
}
