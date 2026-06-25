package com.viettel.docsearch.web.dto;

/** Outcome of a certificate verification. On success {@code certificate} carries the full Phiếu. */
public record VerifyResultDto(
    boolean valid,
    String verifiedVia,   // "FIELDS" (Path b) | "QR" (Path a)
    String message,
    CertificateDto certificate
) {
    public static VerifyResultDto invalid(String message) {
        return new VerifyResultDto(false, null, message, null);
    }

    public static VerifyResultDto ok(String verifiedVia, CertificateDto certificate) {
        return new VerifyResultDto(true, verifiedVia, null, certificate);
    }
}
