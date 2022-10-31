package com.example.newestlinen.utils;

import org.apache.commons.lang.RandomStringUtils;


public class StringUtils {
    public static String cleanString(String str) {
        return str == null ? null : str.trim().toLowerCase();
    }

    public static boolean compareString(String a, String b) {
        if (a == null || b == null) {
            return false;
        }

        return cleanString(a).equals(cleanString(b));
    }

    public static boolean compareStringArea(String a, String b) {
        if (a == null || b == null) {
            return false;
        }

        return cleanString(a).replaceAll("\\s", "").equals(cleanString(b).replaceAll("\\s", ""));
    }

    public static String generateRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length).toUpperCase();
    }
}
