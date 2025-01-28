package com.hus.palindrome.validation.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoNumbersValidatorTest {
    @Test
    void noNumbersValidator_ThrowsException_WhenInputContainsNumbers() {
        NoNumbersValidator validator = new NoNumbersValidator();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate("madam123"));
        assertEquals("Input cannot contain numbers", exception.getMessage());
    }
}