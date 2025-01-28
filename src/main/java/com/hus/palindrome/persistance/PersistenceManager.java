package com.hus.palindrome.persistance;

import java.util.Set;

public interface PersistenceManager {
    void save(String value);
    Set<String> load();
}
