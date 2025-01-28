package com.hus.palindrome.persistance.factory;

import com.hus.palindrome.config.PalindromeProperties;
import com.hus.palindrome.persistance.PersistenceManager;
import com.hus.palindrome.persistance.file.FilePersistenceManager;

public class FilePersistenceFactory implements PersistenceFactory {

    private final PalindromeProperties properties;

    public FilePersistenceFactory(PalindromeProperties properties) {
        this.properties = properties;
    }

    @Override
    public PersistenceManager createPersistenceManager() {
        return new FilePersistenceManager(properties.getCache().getFile());
    }
}
