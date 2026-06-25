package com.viettel.docsearch.web.dto;

/** A "cấm đảm nhiệm chức vụ" entry within the criminal-record content of a certificate. */
public record BanPositionDto(
    String banPosition,
    String decisionNo,
    String decisionDate,
    String court,
    String fromDate
) {}
