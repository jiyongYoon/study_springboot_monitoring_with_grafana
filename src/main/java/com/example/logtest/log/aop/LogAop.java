package com.example.logtest.log.aop;

import com.example.logtest.log.Logger;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class LogAop {

    private final Logger logger;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerAnnotatedClass() {}

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void serviceAnnotatedClass() {}

    @Pointcut("execution(* com.example..*Repository+.*(..))") // 해당 패키지의 Repository로 끝나는 클래스 대상
    public void repositoryClass() {}

    @Around("restControllerAnnotatedClass() || serviceAnnotatedClass() || repositoryClass()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        if (isNotClientRequest()) {
            return joinPoint.proceed();
        }

        String methodName = getMethodName(joinPoint);
        String className = getClassSimpleName(joinPoint);

        logger.methodCall(className, methodName);
        try {
            Object result = joinPoint.proceed();
            logger.methodReturn(className, methodName);
            return result;
        } catch (Throwable e) {
            logger.throwException(className, methodName, e);
            throw e;
        }
    }

    private boolean isNotClientRequest() {
        return Objects.isNull(RequestContextHolder.getRequestAttributes());
    }

    private String getClassSimpleName(ProceedingJoinPoint joinPoint) {
        Class<?> clazz = joinPoint.getTarget().getClass();
        String className = clazz.getSimpleName();
        if (className.contains("Proxy")) {
            className = clazz.getInterfaces()[0].getSimpleName();
        }
        return className;
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }
}
