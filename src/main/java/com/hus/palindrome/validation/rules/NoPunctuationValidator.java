package com.hus.palindrome.validation.rules;

import com.hus.palindrome.validation.ValidationRule;

public class NoPunctuationValidator implements ValidationRule {
    @Override
    public void validate(String text) {
        if (!text.matches("^[a-zA-Z]+$")) {
            throw new IllegalArgumentException("Input cannot contain punctuation or special characters");
        }
    }
}
