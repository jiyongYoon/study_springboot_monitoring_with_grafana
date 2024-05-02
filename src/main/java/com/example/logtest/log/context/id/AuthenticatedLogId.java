package com.example.logtest.log.context.id;

import java.util.UUID;

public class AuthenticatedLogId implements LogId {

    private final String userId;
    private final String requestId;

    public AuthenticatedLogId(Object requestId) {
        this.userId = UUID.randomUUID().toString().substring(0, 8);
        this.requestId = String.valueOf(requestId);
    }

    @Override
    public String logId() {
        return requestId + "(userId" + "-" + userId + ")";
    }
}
