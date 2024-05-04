package com.example.logtest.log.context.id;

import java.util.UUID;

public class AuthenticatedLogId implements LogId {

    private final String userId;
    private final String requestId;

    public AuthenticatedLogId(Object userId, String requestId) {
        this.userId = String.valueOf(userId);
        this.requestId = requestId;
    }

    @Override
    public String logId() {
        return requestId + "(userId" + "-" + userId + ")";
    }
}
