package com.example.logtest.controller;

import com.example.logtest.entity.LogEntity;
import lombok.Builder;
import lombok.Getter;

public class LogDto {
    public record Request(String name) {
        public LogEntity toEntity() {
            return LogEntity.builder().name(name).build();
        }
    }

    @Builder
    @Getter
    public static class Response {
        private Long id;
        private String name;
    }
}
