//package com.example.logtest.decorator;
//
//import org.slf4j.MDC;
//import org.springframework.core.task.TaskDecorator;
//
//import java.util.Map;
//
///** 스프링은 비동기 작업이 시작되기 전/후의 작업을 데코레이트 하는데,
// * 이 작업을 꾸미도록 `TaskDecorator`라는 인터페이스를 제공한다.
// */
//public class MDCTaskDecorator implements TaskDecorator {
//
//    @Override
//    public Runnable decorate(Runnable runnable) {
//        Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();
//        return () -> {
//            MDC.setContextMap(copyOfContextMap);
//            MDC.put("async", "true");
//            runnable.run();
//        };
//    }
//}
