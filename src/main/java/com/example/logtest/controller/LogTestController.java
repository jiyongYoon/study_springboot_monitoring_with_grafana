package com.example.logtest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LogTestController {

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
}
