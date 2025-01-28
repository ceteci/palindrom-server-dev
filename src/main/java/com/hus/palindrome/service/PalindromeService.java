package com.hus.palindrome.service;

import com.hus.palindrome.cache.PalindromeCache;
import com.hus.palindrome.dto.PalindromeRequest;
import com.hus.palindrome.dto.PalindromeResponse;
import com.hus.palindrome.events.CacheUpdateEvent;
import com.hus.palindrome.events.PersistenceSaveEvent;
import com.hus.palindrome.validation.chain.ValidationChain;
import io.github.resilience4j.retry.annotation.Retry;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;

/**
 * Handles palindrome processing, including validation, caching, and persistence operations.
 */
@Service
public class PalindromeService {

    private static final Logger logger = LoggerFactory.getLogger(PalindromeService.class);

    private final ValidationChain validationChain;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final PalindromeCache cache;



    private final Set<String> processedValues = new HashSet<>();

    public PalindromeService(ValidationChain validationChain, ModelMapper modelMapper,
                             ApplicationEventPublisher eventPublisher, PalindromeCache cache) {
        this.validationChain = validationChain;
        this.modelMapper = modelMapper;
        this.eventPublisher = eventPublisher;
        this.cache = cache;
    }

//    @Cacheable(value = "palindromes", key = "#request.text")
    @Retry(name = "palindromeService", fallbackMethod = "fallbackProcessRequest")
    public PalindromeResponse processRequest(PalindromeRequest request) {
        logger.info("Processing palindrome request for user '{}'", request.getUsername());

        // validate the input text
        validationChain.validate(request.getText());
        String text = request.getText();

        // check cache first
        Boolean cachedResult = cache.get(text);
        if (cachedResult != null) {
            logger.info("Cache hit for '{}'", text);
            return createResponse(request, cachedResult);
        }

        boolean isPalindrome = isPalindrome(text);

        eventPublisher.publishEvent(new CacheUpdateEvent(text, isPalindrome));
        // persist if it is a new palindrome
        if (isPalindrome) {
            eventPublisher.publishEvent(new PersistenceSaveEvent(text));
        }

        return createResponse(request, isPalindrome);
    }

    public PalindromeResponse fallbackProcessRequest(PalindromeRequest request, Throwable t) {
        logger.error("Failed to process request for '{}': {}", request.getText(), t.getMessage());
        return new PalindromeResponse(false, request.getText());
    }

    public boolean isPalindrome(String text) {
        String lowerCaseText = text.toLowerCase();
        return lowerCaseText.contentEquals(new StringBuilder(lowerCaseText).reverse());
    }

    private PalindromeResponse createResponse(PalindromeRequest request, boolean isPalindrome) {
        PalindromeResponse response = modelMapper.map(request, PalindromeResponse.class);
        response.setPalindrome(isPalindrome);
        return response;
    }
}
