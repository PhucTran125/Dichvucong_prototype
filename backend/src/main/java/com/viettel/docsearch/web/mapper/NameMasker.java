package com.viettel.docsearch.web.mapper;

public final class NameMasker {

    private NameMasker() {}

    public static String mask(String fullName) {
        if (fullName == null || fullName.isBlank()) return "";
        String[] parts = fullName.trim().split("\\s+");
        if (parts.length == 1) return parts[0];
        if (parts.length == 2) {
            return parts[0] + " " + parts[1].charAt(0);
        }
        StringBuilder sb = new StringBuilder(parts[0]).append(' ');
        for (int i = 1; i < parts.length - 1; i++) {
            if (i > 1) sb.append(' ');
            sb.append(parts[i].charAt(0));
        }
        sb.append("*** ").append(parts[parts.length - 1].charAt(0));
        return sb.toString();
    }
}
