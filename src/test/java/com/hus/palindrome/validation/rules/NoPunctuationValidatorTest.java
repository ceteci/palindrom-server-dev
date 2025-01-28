package com.hus.palindrome.validation.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoPunctuationValidatorTest {
    @Test
    void noPunctuationValidator_ThrowsException_WhenInputContainsPunctuation() {
        NoPunctuationValidator validator = new NoPunctuationValidator();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate("madam!"));
        assertEquals("Input cannot contain punctuation or special characters", exception.getMessage());
    }
}