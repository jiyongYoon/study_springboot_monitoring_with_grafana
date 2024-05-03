package com.example.logtest.log;

import com.example.logtest.log.context.ThreadLocalHolder;
import com.example.logtest.log.context.LogContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Logger {

    private static final String CALL_PREFIX = "--->";
    private static final String RETURN_PREFIX = "<---";
    private static final String EX_PREFIX = "<X--";

    public void methodCall(String className, String methodName) {
        LogContext logContext = ThreadLocalHolder.getThreadLocalLogContextHolder().get();

        logContext.increaseCall();
        log.info("[{}]  {}",
                formattedLogInfo(logContext.logId()),
                formattedClassAndMethod(logContext.depthPrefix(CALL_PREFIX), className, methodName)
        );

        ThreadLocalHolder.getThreadLocalLogContextHolder().set(logContext);
    }

    public void methodReturn(String className, String methodName) {
        LogContext logContext = ThreadLocalHolder.getThreadLocalLogContextHolder().get();

        log.info("[{}]  {}  time={}ms  ",
                formattedLogInfo(logContext.logId()),
                formattedClassAndMethod(logContext.depthPrefix(RETURN_PREFIX), className, methodName),
                logContext.totalTakenTime()
        );
        logContext.decreaseCall();

        ThreadLocalHolder.getThreadLocalLogContextHolder().set(logContext);
    }

    public void throwException(String className, String methodName, Throwable exception) {
        LogContext logContext = ThreadLocalHolder.getThreadLocalLogContextHolder().get();
        log.error("[{}]  {}  time={}ms,  throws {}  ",
                formattedLogInfo(logContext.logId()),
                formattedClassAndMethod(logContext.depthPrefix(EX_PREFIX), className, methodName),
                logContext.totalTakenTime(),
                exception.getClass().getSimpleName()
        );
        logContext.decreaseCall();
    }

    private String formattedLogInfo(String prefix) {
        return "%19s".formatted(prefix);
    }

    private String formattedClassAndMethod(String prefix, String className, String methodName) {
        return String.format("%-80s", prefix + className + "." + methodName + "()");
    }
}
