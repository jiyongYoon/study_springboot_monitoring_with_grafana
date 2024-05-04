package com.example.logtest.log.context.id;

import java.util.Optional;

//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "type")
//@JsonSubTypes({
//        @JsonSubTypes.Type(name = "AnonymousLogId", value = AnonymousLogId.class),
//        @JsonSubTypes.Type(name = "AuthenticatedLogId", value = AuthenticatedLogId.class)
//})
public interface LogId {

    static LogId from(Optional<Long> userId, String requestId) {
        if (userId.isPresent()) {
            return new AuthenticatedLogId(userId.get(), requestId);
        } else {
            return new AnonymousLogId(requestId);
        }
    }


    String logId();
}
