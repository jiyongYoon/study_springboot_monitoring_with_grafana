package com.example.logtest.log.context;

public class ThreadLocalHolder {

    private final static ThreadLocal<LogContext> logContextHolder = new ThreadLocal<>();
    private final static ThreadLocal<QueryCounter> queryCounterHolder = new ThreadLocal<>();

    public static ThreadLocal<LogContext> getThreadLocalLogContextHolder() {
        return logContextHolder;
    }

    public static ThreadLocal<QueryCounter> getThreadLocalQueryCounterHolder() {
        return queryCounterHolder;
    }

    public ThreadLocalHolder(LogContext logContext, QueryCounter queryCounter) {
        logContextHolder.set(logContext);
        queryCounterHolder.set(queryCounter);
    }
}
