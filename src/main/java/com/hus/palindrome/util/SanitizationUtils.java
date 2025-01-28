package com.hus.palindrome.util;

import java.util.Arrays;

public class SanitizationUtils {
    // sanitize sensitive arguments
    public static Object[] sanitizeArgs(Object[] args) {
        if (args == null) {
            return new Object[]{};
        }

        return Arrays.stream(args)
                .map(arg -> {
                    if (arg instanceof String && ((String) arg).toLowerCase().contains("password")) {
                        return "****";
                    }
                    return arg;
                })
                .toArray();
    }
}
