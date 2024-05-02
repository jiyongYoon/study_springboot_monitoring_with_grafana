//package com.example.logtest.interceptor;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.MDC;
//import org.springframework.util.StringUtils;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.util.UUID;
//
//public class MDCLoggingInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        String handlerName = handlerMethod.getMethod().getName();
//        String methodName = handlerMethod.getBeanType().getSimpleName();
//        String controllerInfo = methodName + "." + handlerName;
//        MDC.put("service_name", controllerInfo);
//
//        String requestId = ((HttpServletRequest) request).getHeader("X-Request-ID");
//        MDC.put("request_id",
//                StringUtils.hasText(requestId) ?
//                        requestId : "system-" + UUID.randomUUID().toString().replace("-", ""));
//
//        return true;
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        MDC.clear();
//    }
//}
