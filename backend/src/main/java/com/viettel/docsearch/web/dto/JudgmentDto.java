package com.viettel.docsearch.web.dto;

/** A court judgment (bản án) line within the criminal-record content of a certificate. */
public record JudgmentDto(
    String judgmentNo,
    String judgmentDate,
    String court,
    String crimeNames,
    String penaltyMain,
    String penaltyAdd,
    String terms,
    String executionStatus,
    String remissionDate
) {}
