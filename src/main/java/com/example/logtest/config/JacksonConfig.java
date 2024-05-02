package com.example.logtest.config;

import com.example.logtest.log.context.id.AnonymousLogId;
import com.example.logtest.log.context.id.AuthenticatedLogId;
import com.example.logtest.log.context.id.LogId;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JacksonConfig {
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void setVisibility() {
        this.objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.objectMapper.addMixIn(LogId.class, AnonymousLogId.class);
        this.objectMapper.addMixIn(LogId.class, AuthenticatedLogId.class);
    }
}
