package com.example.logtest.log.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

import java.sql.PreparedStatement;

public class ConnectionMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object proceed = invocation.proceed();
        if (proceed instanceof PreparedStatement ps) {
            ProxyFactory proxyFactory = new ProxyFactory(ps);
            proxyFactory.addAdvice(new PreparedStatementMethodInterceptor());
            return proxyFactory.getProxy();
        }
        return proceed;
    }
}
