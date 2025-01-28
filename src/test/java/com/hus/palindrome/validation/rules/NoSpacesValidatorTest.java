package com.hus.palindrome.validation.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoSpacesValidatorTest {
    @Test
    void noSpacesValidator_ThrowsException_WhenInputContainsSpaces() {
        NoSpacesValidator validator = new NoSpacesValidator();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate("madam madam"));
        assertEquals("Input cannot contain spaces", exception.getMessage());
    }

}