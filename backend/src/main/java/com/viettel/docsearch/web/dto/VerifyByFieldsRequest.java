package com.viettel.docsearch.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Path (b) input — the 04 holder fields printed on the certificate
 * (mã số Phiếu, họ tên, ngày sinh, giới tính). The officer-login gate and the demo
 * captcha are enforced in the frontend, so no auth code is required here.
 */
public record VerifyByFieldsRequest(
    @NotBlank String certificateNumber,
    @NotBlank String fullName,
    @NotBlank String dateOfBirth,   // accepts dd/MM/yyyy or yyyy-MM-dd
    @NotBlank String gender         // "Nam"/"Nữ"/"NAM"/"NU"/"MALE"/"FEMALE"
) {}
