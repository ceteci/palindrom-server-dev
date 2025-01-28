package com.hus.palindrome.validation.chain;

import com.hus.palindrome.validation.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public class ValidationChain {
    private final List<ValidationRule> rules = new ArrayList<>();

    public void addRule(ValidationRule rule) {
        rules.add(rule);
    }

    public void validate(String text) {
        for (ValidationRule rule : rules) {
            rule.validate(text);
        }
    }
}
