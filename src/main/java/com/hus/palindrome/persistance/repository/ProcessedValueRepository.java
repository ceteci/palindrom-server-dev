package com.hus.palindrome.persistance.repository;

import com.hus.palindrome.persistance.entity.ProcessedValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedValueRepository extends JpaRepository<ProcessedValue, String> {
}
