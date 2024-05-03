package com.example.logtest.service;

import com.example.logtest.controller.LogDto;
import com.example.logtest.entity.LogEntity;
import com.example.logtest.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;
    private final ObjectProvider<LogService> logServiceProvider;

    @Transactional
    public LogDto.Response save(LogDto.Request request) {
        LogEntity requestLog = LogEntity.builder().name(request.name()).build();
        LogEntity savedLog = logRepository.save(requestLog);

        logServiceProvider.getObject().printLog(savedLog);

        logServiceProvider.getObject().saveAnotherLog(request);

        return LogDto.Response.builder()
                .id(savedLog.getId())
                .name(savedLog.getName())
                .build();
    }

    @Transactional
    public LogDto.Response saveWarn(LogDto.Request request) {
        LogEntity requestLog = LogEntity.builder().name(request.name()).build();
        LogEntity savedLog = logRepository.save(requestLog);

        logServiceProvider.getObject().printLog(savedLog);

        logServiceProvider.getObject().saveAnotherLog(request);
        logServiceProvider.getObject().saveAnotherLog(request);
        logServiceProvider.getObject().saveAnotherLog(request);
        logServiceProvider.getObject().saveAnotherLog(request);
        logServiceProvider.getObject().saveAnotherLog(request);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return LogDto.Response.builder()
                .id(savedLog.getId())
                .name(savedLog.getName())
                .build();
    }

    @Async
    public void printLog(LogEntity logEntity) {
        log.info("save! id={}, name={}", logEntity.getId(), logEntity.getName());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAnotherLog(LogDto.Request request) {
        LogEntity requestLog2 = LogEntity.builder().name("[requires_new]" + request.name()).build();
        LogEntity savedLog2 = logRepository.save(requestLog2);

        logServiceProvider.getObject().printLog(savedLog2);
    }


}
