package com.viettel.docsearch.web;

import com.viettel.docsearch.config.AppProperties;
import com.viettel.docsearch.lltp.LltpVerificationService;
import com.viettel.docsearch.service.QrService;
import com.viettel.docsearch.web.dto.DemoCertificateCodeDto;
import com.viettel.docsearch.web.dto.ErrorResponse;
import com.viettel.docsearch.web.dto.QrTestResultDto;
import com.viettel.docsearch.web.dto.VerifyByFieldsRequest;
import com.viettel.docsearch.web.dto.VerifyResultDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Điều 17 — Xác thực Phiếu lý lịch tư pháp.
 *
 * <ul>
 *   <li>POST /api/lltp/verify — 04 holder fields (officer-gated + demo captcha in the frontend).</li>
 *   <li>GET  /api/lltp/certificates/{documentNo}/qr.png — the certificate's QR image.</li>
 *   <li>GET  /api/lltp/_demo/codes — dev-only listing of seeded certs + derived codes.</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/lltp")
public class LltpVerificationController {

    private final LltpVerificationService verification;
    private final QrService qr;
    private final AppProperties props;

    public LltpVerificationController(LltpVerificationService verification,
                                      QrService qr,
                                      AppProperties props) {
        this.verification = verification;
        this.qr = qr;
        this.props = props;
    }

    /** Verify by the 04 printed fields. Always returns 200; {@code valid} in the body signals the outcome. */
    @PostMapping("/verify")
    public VerifyResultDto verify(@Valid @RequestBody VerifyByFieldsRequest req) {
        return verification.verifyByFields(req);
    }

    @GetMapping(value = "/certificates/{certificateNumber}/qr.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> qrPng(@PathVariable String certificateNumber) {
        if (!verification.exists(certificateNumber)) {
            return ResponseEntity.status(404).build();
        }
        byte[] png = qr.pngForCertificate(certificateNumber, verification.qrSignatureFor(certificateNumber));
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(png);
    }

    /**
     * Dev/test QR generator — no authentication required. Looks up a mã số Phiếu (RECEIVE_NO);
     * when an active Phiếu exists it returns the QR deep-link + the image URL to render.
     * Always 200; {@code found} in the body signals the outcome.
     */
    @GetMapping("/_demo/qr")
    public QrTestResultDto demoQr(@RequestParam("receiveNo") String receiveNo) {
        return verification.generateTestQr(receiveNo);
    }

    @GetMapping("/_demo/codes")
    public ResponseEntity<?> demoCodes() {
        if (!props.getLltp().isExposeCodes()) {
            return ResponseEntity.status(404).body(new ErrorResponse("NOT_FOUND", "Not available"));
        }
        List<DemoCertificateCodeDto> codes = verification.listDemoCodes();
        return ResponseEntity.ok(codes);
    }
}
