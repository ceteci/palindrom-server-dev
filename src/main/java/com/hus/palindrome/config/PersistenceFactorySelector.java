package com.hus.palindrome.config;

import com.hus.palindrome.persistance.factory.DatabasePersistenceFactory;
import com.hus.palindrome.persistance.factory.FilePersistenceFactory;
import com.hus.palindrome.persistance.factory.PersistenceFactory;
import com.hus.palindrome.persistance.repository.ProcessedValueRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceFactorySelector {

    private final PalindromeProperties properties;
    private final ProcessedValueRepository repository;

    public PersistenceFactorySelector(PalindromeProperties properties, ProcessedValueRepository repository) {
        this.properties = properties;
        this.repository = repository;
    }

    @Bean
    public PersistenceFactory select() {
        String mode = properties.getPersistence().getMode();
        if ("file".equalsIgnoreCase(mode)) {
            return new FilePersistenceFactory(properties);
        } else if ("database".equalsIgnoreCase(mode)) {
            return new DatabasePersistenceFactory(repository);
        }
        throw new IllegalArgumentException("Invalid persistence mode: " + mode);
    }
}
