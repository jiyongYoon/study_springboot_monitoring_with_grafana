package com.example.logtest.repository;

import com.example.logtest.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogEntity, Long> {
}
