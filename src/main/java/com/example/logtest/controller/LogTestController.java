package com.example.logtest.controller;

import com.example.logtest.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LogTestController {

    private final RedisTemplate<String, String> redisTemplate;
    private final LogService logService;

    @GetMapping("/info")
    public void info() {
        log.info("log.info!!");
    }

    @GetMapping("/debug")
    public void debug() {
        log.debug("log.debug!");
    }

    @GetMapping("/trace")
    public void trace() {
        log.trace("log.trace");
    }

    @GetMapping("/warn")
    public void warn() {
        log.warn("log.warn!!!");
    }

    @GetMapping("/e")
    public void error() {
        try {
            throw new RuntimeException("RuntimeException!!!!");
        } catch (Exception e) {
            log.error("log.error!!!!", e);
        }
    }

    @GetMapping("/redis")
    public void redis() {
        for (int i = 0; i < 1000; i++) {
            redisTemplate.opsForValue()
                    .increment("key");
        }
        log.info("redis insert finished!!");
    }

    @PostMapping("/save")
    public LogDto.Response save(@RequestBody LogDto.Request request) {
        return logService.save(request);
    }
}
