package com.viettel.docsearch.web.mapper;

import com.viettel.docsearch.domain.BanPosition;
import com.viettel.docsearch.domain.Declaration;
import com.viettel.docsearch.domain.Judgment;
import com.viettel.docsearch.domain.SynthesisVerification;
import com.viettel.docsearch.lltp.LltpLookups;
import com.viettel.docsearch.web.dto.BanPositionDto;
import com.viettel.docsearch.web.dto.CertificateDto;
import com.viettel.docsearch.web.dto.JudgmentDto;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/** Builds the display {@link CertificateDto} straight from the LLTP entities + label lookups. */
@Component
public class CertificateMapper {

    public CertificateDto toDto(Declaration d, SynthesisVerification sv,
                                List<Judgment> judgments, List<BanPosition> bans) {
        var subject = new CertificateDto.SubjectDto(
            d.getFullName(),
            LltpLookups.genderLabel(d.getGenderId()),
            fmt(d.getBirthDate()),
            d.getIdentifyNo(),
            blankToNull(d.getNationalName()),
            blankToNull(d.getEthnicName()),
            d.getResidence(),
            d.getBirthPlace()
        );

        boolean signed = isNotBlank(sv.getUserSigned());

        return new CertificateDto(
            d.getReceiveNo(),
            d.getJusticeNo(),
            fmt(sv.getIssueDate()),
            sv.getIssuePerson(),
            LltpLookups.issuingAgency(sv.getMinistryJusticeId()),
            fmt(sv.getSynthesisDate()),
            signed,
            signed ? sv.getUserSigned() : null,
            subject,
            LltpLookups.crimeStatusLabel(sv.getStatusCrimeId()),
            LltpLookups.hasCriminalRecord(sv.getStatusCrimeId()),
            LltpLookups.banPositionStatusLabel(sv.getBanPossitionStatusId()),
            judgments.stream().map(this::toJudgment).toList(),
            bans.stream().map(this::toBan).toList()
        );
    }

    private JudgmentDto toJudgment(Judgment j) {
        return new JudgmentDto(
            j.getJudgmentNo(),
            fmt(j.getJudgmentDate()),
            LltpLookups.courtName(j.getCourtId()),
            j.getCrimeNames(),
            j.getPenaltyMain(),
            j.getPenaltyAdd(),
            j.getTerms(),
            j.getExecJudgmentStatus(),
            fmt(j.getRemissionDate())
        );
    }

    private BanPositionDto toBan(BanPosition b) {
        return new BanPositionDto(
            b.getBanPosition(),
            b.getDecisionNo(),
            fmt(b.getDecisionDate()),
            LltpLookups.courtName(b.getCourtId()),
            fmt(b.getFromDate())
        );
    }

    // java.sql.Date does not support toInstant(); SimpleDateFormat handles both it and java.util.Date.
    private static String fmt(Date d) {
        return d == null ? null : new SimpleDateFormat("dd/MM/yyyy").format(d);
    }

    private static boolean isNotBlank(String s) { return s != null && !s.isBlank(); }
    private static String blankToNull(String s) { return isNotBlank(s) ? s : null; }
}
