package com.viettel.docsearch.web.dto;

import com.viettel.docsearch.domain.AccountType;

import java.time.LocalDate;
import java.util.UUID;

public record UserDto(
    UUID id,
    String fullName,
    LocalDate dob,
    String citizenId,
    String address,
    AccountType accountType
) {}
