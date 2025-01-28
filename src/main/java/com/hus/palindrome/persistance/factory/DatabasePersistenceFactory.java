package com.hus.palindrome.persistance.factory;

import com.hus.palindrome.persistance.PersistenceManager;
import com.hus.palindrome.persistance.db.DatabasePersistenceManager;
import com.hus.palindrome.persistance.repository.ProcessedValueRepository;

public class DatabasePersistenceFactory implements PersistenceFactory {

    private final ProcessedValueRepository repository;

    public DatabasePersistenceFactory(ProcessedValueRepository repository) {
        this.repository = repository;
    }

    @Override
    public PersistenceManager createPersistenceManager() {
        return new DatabasePersistenceManager(repository);
    }
}