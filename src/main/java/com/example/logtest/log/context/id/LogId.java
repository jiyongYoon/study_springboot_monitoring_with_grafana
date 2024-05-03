package com.example.logtest.log.context.id;

//@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "type")
//@JsonSubTypes({
//        @JsonSubTypes.Type(name = "AnonymousLogId", value = AnonymousLogId.class),
//        @JsonSubTypes.Type(name = "AuthenticatedLogId", value = AuthenticatedLogId.class)
//})
public interface LogId {
    /*
    static LogId fromRequest(UserDetailsImpl 이런식으로) {
        jwt 인증한 securitycontext에서 꺼내서 만들기
    }
     */

    String logId();
}
