package com.hus.palindrome.validation.rules;

import com.hus.palindrome.validation.ValidationRule;

public class NoNumbersValidator implements ValidationRule {
    @Override
    public void validate(String text) {
        if (text.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Input cannot contain numbers");
        }
    }
}
