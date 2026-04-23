package com.viettel.docsearch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private String publicBaseUrl = "http://localhost:4200";
    private Jwt jwt = new Jwt();
    private boolean exportQr = false;

    public String getPublicBaseUrl() { return publicBaseUrl; }
    public void setPublicBaseUrl(String v) { this.publicBaseUrl = v; }
    public Jwt getJwt() { return jwt; }
    public void setJwt(Jwt v) { this.jwt = v; }
    public boolean isExportQr() { return exportQr; }
    public void setExportQr(boolean v) { this.exportQr = v; }

    public static class Jwt {
        private String secret = "";
        private int ttlMinutes = 120;
        public String getSecret() { return secret; }
        public void setSecret(String v) { this.secret = v; }
        public int getTtlMinutes() { return ttlMinutes; }
        public void setTtlMinutes(int v) { this.ttlMinutes = v; }
    }
}
