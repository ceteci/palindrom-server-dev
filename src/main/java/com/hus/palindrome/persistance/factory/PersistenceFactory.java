package com.hus.palindrome.persistance.factory;

import com.hus.palindrome.persistance.PersistenceManager;

public interface PersistenceFactory {
    PersistenceManager createPersistenceManager();
}
