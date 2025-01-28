package com.hus.palindrome.validation;

import com.hus.palindrome.validation.chain.ValidationChain;
import com.hus.palindrome.validation.rules.NoNumbersValidator;
import com.hus.palindrome.validation.rules.NoPunctuationValidator;
import com.hus.palindrome.validation.rules.NoSpacesValidator;
import com.hus.palindrome.validation.rules.NullOrEmptyValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    @Bean
    public ValidationChain validationChain() {
        ValidationChain chain = new ValidationChain();
        chain.addRule(new NullOrEmptyValidator());
        chain.addRule(new NoNumbersValidator());
        chain.addRule(new NoSpacesValidator());
        chain.addRule(new NoPunctuationValidator());
        return chain;
    }
}
