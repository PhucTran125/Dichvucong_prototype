package com.viettel.docsearch.web.dto;

import java.time.LocalDate;
import java.util.UUID;

public record UserDto(
    UUID id,
    String fullName,
    LocalDate dob,
    String citizenId,
    String address
) {}
