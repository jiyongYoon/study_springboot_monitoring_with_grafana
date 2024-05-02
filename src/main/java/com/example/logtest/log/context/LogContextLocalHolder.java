package com.example.logtest.log.context;

public class LogContextLocalHolder {

    private final static ThreadLocal<LogContext> logContextHolder = new ThreadLocal<>();

//    private LogContext logContext;
    // QueryCounter도 같이 받자

    public static ThreadLocal<LogContext> getInstance() {
        return logContextHolder;
    }

    public LogContextLocalHolder(LogContext logContext) {
        logContextHolder.set(logContext);
    }
}
