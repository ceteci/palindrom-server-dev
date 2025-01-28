package com.hus.palindrome.persistance.file;

import com.hus.palindrome.persistance.PersistenceManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FilePersistenceManager implements PersistenceManager {
    private final File file;

    public FilePersistenceManager(String filePath) {
        this.file = new File(filePath);
    }

    @Override
    public void save(String value) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(value);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save value to file", e);
        }
    }

    @Override
    public Set<String> load() {
        Set<String> values = new HashSet<>();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    values.add(line.trim());
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to load values from file", e);
            }
        }
        return values;
    }
}
