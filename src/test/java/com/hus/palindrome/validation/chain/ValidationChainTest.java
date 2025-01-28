package com.hus.palindrome.validation.chain;

import com.hus.palindrome.validation.rules.NoNumbersValidator;
import com.hus.palindrome.validation.rules.NoPunctuationValidator;
import com.hus.palindrome.validation.rules.NoSpacesValidator;
import com.hus.palindrome.validation.rules.NullOrEmptyValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationChainTest {
    @Test
    void validationChain_ThrowsException_WhenAnyRuleFails() {
        ValidationChain chain = new ValidationChain();
        chain.addRule(new NullOrEmptyValidator());
        chain.addRule(new NoNumbersValidator());
        chain.addRule(new NoSpacesValidator());
        chain.addRule(new NoPunctuationValidator());

        assertThrows(IllegalArgumentException.class, () -> chain.validate("madam123"));
        assertThrows(IllegalArgumentException.class, () -> chain.validate("madam madam"));
        assertThrows(IllegalArgumentException.class, () -> chain.validate(null));
        assertThrows(IllegalArgumentException.class, () -> chain.validate("madam!"));
        assertThrows(IllegalArgumentException.class, () -> chain.validate(StringUtils.EMPTY));
    }
}