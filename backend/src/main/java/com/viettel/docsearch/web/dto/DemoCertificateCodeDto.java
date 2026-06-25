package com.viettel.docsearch.web.dto;

/**
 * Dev-only convenience row: everything a tester needs to exercise both verification paths for a
 * seeded certificate. Exposed by GET /api/lltp/_demo/codes when app.lltp.expose-codes=true.
 */
public record DemoCertificateCodeDto(
    String certificateNumber,
    String fullName,
    String dateOfBirth,
    String gender,
    String identifyNo,
    String issueDate,
    String crimeStatus,
    String authCode,        // for Path b
    String qrSignature,     // for Path a
    String verifyQrUrl      // deep-link the QR encodes
) {}
