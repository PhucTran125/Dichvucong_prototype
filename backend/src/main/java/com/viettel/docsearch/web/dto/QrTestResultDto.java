package com.viettel.docsearch.web.dto;

/**
 * Result of the dev/test QR generator (POST /api/lltp/_demo/qr).
 *
 * @param found     true when an active Phiếu exists for the supplied RECEIVE_NO
 * @param receiveNo the normalised mã số Phiếu that was looked up
 * @param deepLink  the verification deep-link encoded in the QR (null when not found)
 * @param qrPngUrl  relative URL of the QR image to render (null when not found)
 */
public record QrTestResultDto(
    boolean found,
    String receiveNo,
    String deepLink,
    String qrPngUrl
) {
    public static QrTestResultDto notFound(String receiveNo) {
        return new QrTestResultDto(false, receiveNo, null, null);
    }
}
