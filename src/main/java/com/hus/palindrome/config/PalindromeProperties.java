package com.hus.palindrome.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "palindrome")
public class PalindromeProperties {
    private final CacheSettings cache = new CacheSettings();
    private final PersistenceSettings persistence = new PersistenceSettings();

    public CacheSettings getCache() {
        return cache;
    }

    public PersistenceSettings getPersistence() {
        return persistence;
    }

    /**
     * Nested class to map "palindrome.cache.*" properties.
     */
    public static class CacheSettings {
        private String file;
        private int size;

        public String getFile() {
            return file;
        }
        public void setFile(String file) {
            this.file = file;
        }

        public int getSize() {
            return size;
        }
        public void setSize(int size) {
            this.size = size;
        }
    }

    /**
     * Nested class to map "palindrome.persistence.*" properties.
     */
    public static class PersistenceSettings {
        /**
         * Possible values: "file", "database"
         */
        private String mode;

        public String getMode() {
            return mode;
        }
        public void setMode(String mode) {
            this.mode = mode;
        }
    }
}
