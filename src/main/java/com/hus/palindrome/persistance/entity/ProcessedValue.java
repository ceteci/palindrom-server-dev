package com.hus.palindrome.persistance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "processed_values")
public class ProcessedValue {
    @Id
    private String text_value;

    public ProcessedValue() {
    }

    public ProcessedValue(String value) {
        this.text_value = value;
    }

    public String getValue() {
        return text_value;
    }

    public void setValue(String value) {
        this.text_value = value;
    }
}
