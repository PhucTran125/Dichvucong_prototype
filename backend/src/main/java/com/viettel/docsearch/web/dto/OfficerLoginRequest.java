package com.viettel.docsearch.web.dto;

import jakarta.validation.constraints.NotBlank;

/** Mock cán bộ (official) login — preset username/password checked against {@code app.officer.*}. */
public record OfficerLoginRequest(
    @NotBlank String username,
    @NotBlank String password
) {}
