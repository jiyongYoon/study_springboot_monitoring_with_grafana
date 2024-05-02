package com.example.logtest.log.context.id;

import java.util.UUID;

public class AnonymousLogId implements LogId {

    private final String id;

    public AnonymousLogId(String id) {
        this.id = id;
    }

    public static AnonymousLogId randomId() {
        return new AnonymousLogId(UUID.randomUUID().toString().substring(0, 8));
    }

    @Override
    public String logId() {
        return id + "(anonymous)";
    }
}
