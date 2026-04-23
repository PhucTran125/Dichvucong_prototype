package com.viettel.docsearch.web.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordLoginRequest(
    @NotBlank String citizenId,
    @NotBlank String password
) {}
