package com.viettel.docsearch.lltp;

import com.viettel.docsearch.config.AppProperties;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

/**
 * Derives and verifies the two opaque tokens that protect Điều 17 verification:
 *
 * <ul>
 *   <li><b>auth code</b> (mã xác thực) — a short human-typeable code printed on the certificate,
 *       checked together with the 4 holder fields in the manual (Path b) flow;</li>
 *   <li><b>QR signature</b> — an authenticity token embedded in the certificate's QR deep-link,
 *       checked in the org-login (Path a) flow.</li>
 * </ul>
 *
 * Both are HMAC-SHA256 derivations keyed by {@code app.lltp.code-secret}, so nothing has to be
 * stored on (or added to) the production Oracle tables — the same secret reproduces the code.
 */
@Service
public class CertificateCodeService {

    // Crockford-style base32 alphabet without easily-confused chars (no I, L, O, U).
    private static final char[] BASE32 = "0123456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray();
    private static final int AUTH_CODE_LEN = 8;

    private final byte[] secret;

    public CertificateCodeService(AppProperties props) {
        String s = props.getLltp().getCodeSecret();
        if (s == null || s.isBlank()) {
            throw new IllegalStateException("app.lltp.code-secret must be configured");
        }
        this.secret = s.getBytes(StandardCharsets.UTF_8);
    }

    /** Auth code bound to a specific certificate + holder, formatted like {@code K7P2-9QXM}. */
    public String authCodeFor(String certNo, String identifyNo) {
        byte[] mac = hmac("authcode|" + norm(certNo) + "|" + norm(identifyNo));
        StringBuilder sb = new StringBuilder(AUTH_CODE_LEN + 1);
        for (int i = 0; i < AUTH_CODE_LEN; i++) {
            if (i == AUTH_CODE_LEN / 2) sb.append('-');
            sb.append(BASE32[mac[i] & 0x1f]);
        }
        return sb.toString();
    }

    /** Constant-time check of a user-supplied auth code (case-insensitive, dashes/spaces ignored). */
    public boolean authCodeMatches(String certNo, String identifyNo, String supplied) {
        if (supplied == null) return false;
        String expected = stripSeparators(authCodeFor(certNo, identifyNo));
        String actual = stripSeparators(supplied).toUpperCase();
        return constantTimeEquals(expected, actual);
    }

    /** Opaque signature embedded in the certificate QR deep-link. */
    public String qrSignatureFor(String certNo) {
        byte[] mac = hmac("qrsig|" + norm(certNo));
        // First 12 bytes are plenty to deter forgery in a prototype; hex is URL-safe.
        return HexFormat.of().formatHex(mac, 0, 12);
    }

    public boolean qrSignatureMatches(String certNo, String supplied) {
        if (supplied == null) return false;
        return constantTimeEquals(qrSignatureFor(certNo), supplied.trim().toLowerCase());
    }

    private byte[] hmac(String message) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret, "HmacSHA256"));
            return mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalStateException("Failed to derive certificate code", e);
        }
    }

    private static String norm(String s) { return s == null ? "" : s.trim(); }

    private static String stripSeparators(String s) {
        return s.replace("-", "").replace(" ", "");
    }

    private static boolean constantTimeEquals(String a, String b) {
        return MessageDigest.isEqual(a.getBytes(StandardCharsets.UTF_8), b.getBytes(StandardCharsets.UTF_8));
    }
}
