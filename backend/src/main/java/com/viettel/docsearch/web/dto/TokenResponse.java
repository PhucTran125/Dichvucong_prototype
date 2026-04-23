package com.viettel.docsearch.web.dto;

public record TokenResponse(
    String accessToken,
    long expiresInSeconds,
    UserDto user
) {}
