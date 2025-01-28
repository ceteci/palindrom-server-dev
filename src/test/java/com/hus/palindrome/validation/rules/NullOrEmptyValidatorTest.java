package com.hus.palindrome.validation.rules;

import com.hus.palindrome.validation.rules.NullOrEmptyValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NullOrEmptyValidatorTest {
    @Test
    void nullValidator_ThrowsException_WhenInputIsNull() {
        NullOrEmptyValidator validator = new NullOrEmptyValidator();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate(null));
        assertEquals("Text cannot be null or empty", exception.getMessage());
    }

    @Test
    void emptyValidator_ThrowsException_WhenInputIsNull() {
        NullOrEmptyValidator validator = new NullOrEmptyValidator();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> validator.validate(StringUtils.EMPTY));
        assertEquals("Text cannot be null or empty", exception.getMessage());
    }
}