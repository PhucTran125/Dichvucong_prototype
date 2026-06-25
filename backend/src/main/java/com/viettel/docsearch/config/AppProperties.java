package com.viettel.docsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String publicBaseUrl = "http://localhost:4200";
    private Jwt jwt = new Jwt();
    private boolean exportQr = false;
    private Lltp lltp = new Lltp();
    private Officer officer = new Officer();

    public String getPublicBaseUrl() { return publicBaseUrl; }
    public void setPublicBaseUrl(String v) { this.publicBaseUrl = v; }
    public Jwt getJwt() { return jwt; }
    public void setJwt(Jwt v) { this.jwt = v; }
    public boolean isExportQr() { return exportQr; }
    public void setExportQr(boolean v) { this.exportQr = v; }
    public Lltp getLltp() { return lltp; }
    public void setLltp(Lltp v) { this.lltp = v; }
    public Officer getOfficer() { return officer; }
    public void setOfficer(Officer v) { this.officer = v; }

    public static class Jwt {
        private String secret = "";
        private int ttlMinutes = 120;
        public String getSecret() { return secret; }
        public void setSecret(String v) { this.secret = v; }
        public int getTtlMinutes() { return ttlMinutes; }
        public void setTtlMinutes(int v) { this.ttlMinutes = v; }
    }

    /** Phiếu lý lịch tư pháp (criminal-record certificate) verification — Điều 17. */
    public static class Lltp {
        /** Secret used to derive/verify the printed auth code (mã xác thực) and QR signature. */
        private String codeSecret = "";
        /** Dev convenience: expose GET /api/lltp/_demo/codes listing seeded certs + their auth codes. */
        private boolean exposeCodes = false;
        public String getCodeSecret() { return codeSecret; }
        public void setCodeSecret(String v) { this.codeSecret = v; }
        public boolean isExposeCodes() { return exposeCodes; }
        public void setExposeCodes(boolean v) { this.exposeCodes = v; }
    }

    /**
     * Mock cán bộ (official) login that gates the LLTP verification page. This is a pure
     * config-only identity — no database row is required; the fields below populate the JWT
     * subject and the UserDto returned on login.
     */
    public static class Officer {
        private String username = "canbo";
        private String password = "123456";
        /** Synthetic JWT subject for the officer (any fixed UUID). */
        private String id = "44444444-4444-4444-4444-000000000001";
        private String fullName = "Cán bộ Sở Tư pháp";
        private String citizenId = "000000000099";
        private String address = "Sở Tư pháp Hà Nội";
        private LocalDate dob = LocalDate.of(1985, 1, 1);
        public String getUsername() { return username; }
        public void setUsername(String v) { this.username = v; }
        public String getPassword() { return password; }
        public void setPassword(String v) { this.password = v; }
        public String getId() { return id; }
        public void setId(String v) { this.id = v; }
        public String getFullName() { return fullName; }
        public void setFullName(String v) { this.fullName = v; }
        public String getCitizenId() { return citizenId; }
        public void setCitizenId(String v) { this.citizenId = v; }
        public String getAddress() { return address; }
        public void setAddress(String v) { this.address = v; }
        public LocalDate getDob() { return dob; }
        public void setDob(LocalDate v) { this.dob = v; }
    }
}
