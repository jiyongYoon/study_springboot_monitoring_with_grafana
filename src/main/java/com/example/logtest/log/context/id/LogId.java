package com.example.logtest.log.context.id;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.web.context.request.RequestContextHolder;

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
