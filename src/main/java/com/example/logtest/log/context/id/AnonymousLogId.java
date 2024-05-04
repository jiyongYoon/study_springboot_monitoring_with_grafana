package com.example.logtest.log.context.id;

import java.util.UUID;

public class AnonymousLogId implements LogId {

    private final String requestId;

    public AnonymousLogId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String logId() {
        return requestId + "(anonymous)";
    }
}
