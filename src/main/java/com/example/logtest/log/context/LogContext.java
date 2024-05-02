package com.example.logtest.log.context;

import com.example.logtest.log.context.id.LogId;

public class LogContext {
    private final LogId logid;
    private final Long startTimeMillis;
    private Integer methodDepth = 0;

    public LogContext(LogId logid) {
        this.logid = logid;
        this.startTimeMillis = System.currentTimeMillis();
    }

    public void increaseCall() {
        methodDepth = methodDepth + 1;
    }

    public void decreaseCall() {
        methodDepth = methodDepth - 1;
    }

    public String logId() {
        return logid.logId();
    }

    public String depthPrefix(String prefixString) {
        if (methodDepth == 1) {
            return "|" + prefixString;
        }
        String bar = "|" + " ".repeat(prefixString.length());
        return bar.repeat(methodDepth - 1) + "|" + prefixString;
    }

    public long totalTakenTime() {
        return System.currentTimeMillis() - startTimeMillis;
    }
}
