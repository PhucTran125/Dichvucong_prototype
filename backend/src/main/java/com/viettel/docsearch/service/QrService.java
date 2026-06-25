package com.viettel.docsearch.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.viettel.docsearch.config.AppProperties;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class QrService {

    private static final int SIZE = 512;

    private final AppProperties props;

    public QrService(AppProperties props) {
        this.props = props;
    }

    private String base() {
        String base = props.getPublicBaseUrl();
        return base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
    }

    public String deepLinkFor(UUID documentId) {
        return base() + "/search?id=" + documentId;
    }

    /**
     * Deep-link encoded in a Phiếu LLTP's QR (Điều 17): lands on the officer-gated verification
     * page with the certificate number pre-filled, ready for the cán bộ to enter the other fields.
     * The {@code signature} arg is kept for call-site stability but is no longer part of the link.
     */
    public String deepLinkForCertificate(String documentNo, String signature) {
        return base() + "/verify?doc=" + URLEncoder.encode(documentNo, StandardCharsets.UTF_8);
    }

    public byte[] pngForCertificate(String documentNo, String signature) {
        return png(deepLinkForCertificate(documentNo, signature));
    }

    public byte[] pngFor(UUID documentId) {
        return png(deepLinkFor(documentId));
    }

    private byte[] png(String link) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.MARGIN, 2);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix matrix = new QRCodeWriter().encode(link, BarcodeFormat.QR_CODE, SIZE, SIZE, hints);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", baos);
            return baos.toByteArray();
        } catch (WriterException | IOException e) {
            throw new IllegalStateException("Failed to render QR for " + link, e);
        }
    }
}
