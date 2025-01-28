package com.hus.palindrome.persistance.db;

import com.hus.palindrome.persistance.PersistenceManager;
import com.hus.palindrome.persistance.entity.ProcessedValue;
import com.hus.palindrome.persistance.repository.ProcessedValueRepository;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DatabasePersistenceManager implements PersistenceManager {
    private final ProcessedValueRepository repository;

    public DatabasePersistenceManager(ProcessedValueRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(String value) {
        repository.save(new ProcessedValue(value));
    }

    @Override
    public Set<String> load() {
        Set<String> values = new HashSet<>();
        for (ProcessedValue processedValue : repository.findAll()) {
            values.add(processedValue.getValue());
        }
        return values;
    }
}
