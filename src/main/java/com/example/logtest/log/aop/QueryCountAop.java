package com.example.logtest.log.aop;

import com.example.logtest.log.interceptor.ConnectionMethodInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class QueryCountAop {

    public QueryCountAop() {}

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object getConnection(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object connection = proceedingJoinPoint.proceed();
        ProxyFactory proxyFactory = new ProxyFactory(connection);
        proxyFactory.addAdvice(new ConnectionMethodInterceptor());
        return proxyFactory.getProxy();
    }
}
