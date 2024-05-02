package com.example.logtest.decorator;

import com.example.logtest.log.context.LogContext;
import com.example.logtest.log.context.LogContextLocalHolder;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

/** 스프링은 비동기 작업이 시작되기 전/후의 작업을 데코레이트 하는데,
 * 이 작업을 꾸미도록 `TaskDecorator`라는 인터페이스를 제공한다.
 */
public class ThreadLocalTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        LogContext logContext = LogContextLocalHolder.getInstance().get();
        return () -> {
            LogContextLocalHolder.getInstance().set(logContext);
            runnable.run();
        };
    }
}
