package com.viettel.docsearch.web.dto;

import java.util.List;

/**
 * The full Phiếu lý lịch tư pháp returned once a verification succeeds (both Path a and Path b
 * return the full certificate). Dates are pre-formatted dd/MM/yyyy strings for direct display.
 */
public record CertificateDto(
    String certificateNumber,   // SYNTHESIS_VERIFICATION.DOCUMENT_NO — mã số Phiếu
    String justiceNo,           // DECLARATION.JUSTICE_NO — số Phiếu LLTP
    String issueDate,           // ngày cấp
    String issuePerson,         // người cấp
    String issuingAgency,       // cơ quan cấp
    String synthesisDate,
    boolean digitallySigned,
    String signedBy,
    SubjectDto subject,
    String crimeStatus,         // "Không có án tích" / "Có án tích"
    boolean hasCriminalRecord,
    String banPositionStatus,
    List<JudgmentDto> judgments,
    List<BanPositionDto> banPositions
) {
    public record SubjectDto(
        String fullName,
        String gender,
        String dateOfBirth,
        String identifyNo,
        String nationality,
        String ethnicity,
        String residence,
        String birthPlace
    ) {}
}
