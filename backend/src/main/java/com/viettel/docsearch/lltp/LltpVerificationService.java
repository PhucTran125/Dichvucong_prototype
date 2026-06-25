package com.viettel.docsearch.lltp;

import com.viettel.docsearch.domain.BanPosition;
import com.viettel.docsearch.domain.Declaration;
import com.viettel.docsearch.domain.Judgment;
import com.viettel.docsearch.domain.SynthesisVerification;
import com.viettel.docsearch.repo.BanPositionRepository;
import com.viettel.docsearch.repo.DeclarationRepository;
import com.viettel.docsearch.repo.JudgmentRepository;
import com.viettel.docsearch.repo.SynthesisVerificationRepository;
import com.viettel.docsearch.search.SearchTextFolder;
import com.viettel.docsearch.service.QrService;
import com.viettel.docsearch.web.dto.DemoCertificateCodeDto;
import com.viettel.docsearch.web.dto.QrTestResultDto;
import com.viettel.docsearch.web.dto.VerifyByFieldsRequest;
import com.viettel.docsearch.web.dto.VerifyResultDto;
import com.viettel.docsearch.web.mapper.CertificateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Điều 17 verification logic for Phiếu lý lịch tư pháp.
 *
 * <p>Resolves the certificate by finding the {@link Declaration} by its RECEIVE_NO (mã số Phiếu);
 * if it exists, loads the corresponding {@link SynthesisVerification} (the issued Phiếu) by
 * declaration_id, plus its judgments / ban-positions.
 *
 * <p>{@link #verifyByFields}: the declaration must also match họ tên / ngày sinh / giới tính.
 * The officer-login gate and demo captcha are enforced in the frontend.
 *
 * <p>Failures use a single generic message so the endpoint never reveals which field was wrong.
 */
@Service
public class LltpVerificationService {

    private static final String GENERIC_FAILURE =
        "Phiếu lý lịch tư pháp không hợp lệ hoặc thông tin cung cấp không chính xác.";

    private static final String ACTIVE = "1";

    private static final DateTimeFormatter[] DOB_FORMATS = {
        DateTimeFormatter.ofPattern("d/M/yyyy"),
        DateTimeFormatter.ofPattern("yyyy-M-d")
    };

    private final DeclarationRepository declarations;
    private final SynthesisVerificationRepository synthesis;
    private final JudgmentRepository judgments;
    private final BanPositionRepository banPositions;
    private final CertificateCodeService codes;
    private final CertificateMapper mapper;
    private final QrService qr;

    public LltpVerificationService(DeclarationRepository declarations,
                                   SynthesisVerificationRepository synthesis,
                                   JudgmentRepository judgments,
                                   BanPositionRepository banPositions,
                                   CertificateCodeService codes,
                                   CertificateMapper mapper,
                                   QrService qr) {
        this.declarations = declarations;
        this.synthesis = synthesis;
        this.judgments = judgments;
        this.banPositions = banPositions;
        this.codes = codes;
        this.mapper = mapper;
        this.qr = qr;
    }

    /** Path (b): mã số Phiếu (RECEIVE_NO) + họ tên + ngày sinh + giới tính. */
    @Transactional(readOnly = true)
    public VerifyResultDto verifyByFields(VerifyByFieldsRequest req) {
        Declaration d = findDeclaration(req.certificateNumber()).orElse(null);
        if (d == null) return VerifyResultDto.invalid(GENERIC_FAILURE);

        boolean ok = nameMatches(d.getFullName(), req.fullName())
            && dobMatches(d.getBirthDate(), req.dateOfBirth())
            && LltpLookups.genderMatches(d.getGenderId(), req.gender());

        if (!ok) return VerifyResultDto.invalid(GENERIC_FAILURE);
        return certificateFor(d, "FIELDS");
    }

    /** The signature a freshly-issued certificate's QR would carry (for QR generation/demo). */
    public String qrSignatureFor(String certificateNumber) {
        return codes.qrSignatureFor(normalizeCertNo(certificateNumber));
    }

    /** True when a declaration with this RECEIVE_NO exists and has an issued (active) Phiếu. */
    @Transactional(readOnly = true)
    public boolean exists(String certificateNumber) {
        Declaration d = findDeclaration(certificateNumber).orElse(null);
        return d != null && activeSynthesis(d).isPresent();
    }

    /**
     * Dev/test helper: resolve a mã số Phiếu (RECEIVE_NO) and, when an active Phiếu exists,
     * return the verification deep-link that the QR encodes. No authentication required.
     */
    @Transactional(readOnly = true)
    public QrTestResultDto generateTestQr(String certificateNumber) {
        String receiveNo = normalizeCertNo(certificateNumber);
        if (!exists(receiveNo)) {
            return QrTestResultDto.notFound(receiveNo);
        }
        String deepLink = qr.deepLinkForCertificate(receiveNo, codes.qrSignatureFor(receiveNo));
        String qrPngUrl = "/api/lltp/certificates/" + URLEncoder.encode(receiveNo, StandardCharsets.UTF_8) + "/qr.png";
        return new QrTestResultDto(true, receiveNo, deepLink, qrPngUrl);
    }

    /** Dev-only: every seeded certificate with the inputs needed to test both verification paths. */
    @Transactional(readOnly = true)
    public List<DemoCertificateCodeDto> listDemoCodes() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        List<DemoCertificateCodeDto> out = new ArrayList<>();
        for (Declaration d : declarations.findAll()) {
            SynthesisVerification sv = activeSynthesis(d).orElse(null);
            if (sv == null) continue;
            String receiveNo = normalizeCertNo(d.getReceiveNo());
            out.add(new DemoCertificateCodeDto(
                receiveNo,
                d.getFullName(),
                d.getBirthDate() == null ? null : fmt.format(d.getBirthDate()),
                LltpLookups.genderLabel(d.getGenderId()),
                d.getIdentifyNo(),
                sv.getIssueDate() == null ? null : fmt.format(sv.getIssueDate()),
                LltpLookups.crimeStatusLabel(sv.getStatusCrimeId()),
                codes.authCodeFor(receiveNo, d.getIdentifyNo()),
                codes.qrSignatureFor(receiveNo),
                qr.deepLinkForCertificate(receiveNo, codes.qrSignatureFor(receiveNo))
            ));
        }
        return out;
    }

    /** Load the issued Phiếu + án tích content for a matched declaration and map to the DTO. */
    private VerifyResultDto certificateFor(Declaration d, String verifiedVia) {
        SynthesisVerification sv = activeSynthesis(d).orElse(null);
        if (sv == null) return VerifyResultDto.invalid(GENERIC_FAILURE); // no issued Phiếu
        List<Judgment> js = judgments.findByDeclarationId_DeclarationIdAndIsActive(d.getDeclarationId(), ACTIVE);
        List<BanPosition> bs = banPositions.findByDeclarationId_DeclarationIdAndIsActive(d.getDeclarationId(), ACTIVE);
        return VerifyResultDto.ok(verifiedVia, mapper.toDto(d, sv, js, bs));
    }

    private Optional<Declaration> findDeclaration(String certificateNumber) {
        return declarations.findByReceiveNo(normalizeCertNo(certificateNumber)).stream().findFirst();
    }

    private Optional<SynthesisVerification> activeSynthesis(Declaration d) {
        return synthesis.findByDeclarationId_DeclarationIdAndIsActive(d.getDeclarationId(), ACTIVE)
            .stream().findFirst();
    }

    // The certificate is keyed by DECLARATION.RECEIVE_NO (mã số Phiếu); trim-only normalisation.
    private static String normalizeCertNo(String raw) {
        return raw == null ? "" : raw.trim();
    }

    private static boolean nameMatches(String stored, String supplied) {
        return canon(stored).equals(canon(supplied));
    }

    private static String canon(String s) {
        if (s == null) return "";
        return SearchTextFolder.fold(s.trim()).replaceAll("\\s+", " ");
    }

    private static boolean dobMatches(Date storedDob, String suppliedDob) {
        if (storedDob == null) return false;
        LocalDate supplied = parseDob(suppliedDob);
        if (supplied == null) return false;
        String storedIso = new SimpleDateFormat("yyyy-MM-dd").format(storedDob);
        return storedIso.equals(supplied.toString());
    }

    private static LocalDate parseDob(String input) {
        if (input == null || input.isBlank()) return null;
        String s = input.trim();
        for (DateTimeFormatter fmt : DOB_FORMATS) {
            try {
                return LocalDate.parse(s, fmt);
            } catch (Exception ignored) {
                // try next format
            }
        }
        return null;
    }
}
