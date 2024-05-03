package com.example.logtest.log.interceptor;

import com.example.logtest.log.context.ThreadLocalHolder;
import com.example.logtest.log.context.QueryCounter;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.Method;
import java.util.Objects;

public class PreparedStatementMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (isExecuteQuery(invocation.getMethod()) && isRequest()) {
            QueryCounter queryCounter = ThreadLocalHolder.getThreadLocalQueryCounterHolder().get();
            queryCounter.increase();
        }
        return invocation.proceed();
    }

    private boolean isExecuteQuery(Method method) {
        String methodName = method.getName();
        return methodName.equals("executeQuery") || methodName.equals("execute") || methodName.equals("executeUpdate");
    }

    private boolean isRequest() {
        return Objects.nonNull(RequestContextHolder.getRequestAttributes());
    }
}
