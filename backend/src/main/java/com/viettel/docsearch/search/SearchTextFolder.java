package com.viettel.docsearch.search;

import java.text.Normalizer;
import java.util.regex.Pattern;

public final class SearchTextFolder {

    private static final Pattern COMBINING = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    private SearchTextFolder() {}

    public static String fold(String input) {
        if (input == null || input.isEmpty()) return "";
        String decomposed = Normalizer.normalize(input, Normalizer.Form.NFD);
        String stripped = COMBINING.matcher(decomposed).replaceAll("");
        return stripped.replace('đ', 'd').replace('Đ', 'D').toLowerCase();
    }
}
