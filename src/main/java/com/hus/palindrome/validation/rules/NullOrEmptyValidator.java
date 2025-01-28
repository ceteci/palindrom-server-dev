package com.hus.palindrome.validation.rules;

import com.hus.palindrome.validation.ValidationRule;

public class NullOrEmptyValidator implements ValidationRule {
    @Override
    public void validate(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
    }
}
