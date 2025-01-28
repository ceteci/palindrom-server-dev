package com.hus.palindrome.validation.rules;

import com.hus.palindrome.validation.ValidationRule;

public class NoSpacesValidator implements ValidationRule {
    @Override
    public void validate(String text) {
        if (text.contains(" ")) {
            throw new IllegalArgumentException("Input cannot contain spaces");
        }
    }
}
