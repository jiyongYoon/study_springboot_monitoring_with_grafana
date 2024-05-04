package com.example.logtest.log.interceptor;

import com.example.logtest.log.RequestInfoLogData;
import com.example.logtest.log.context.LogContext;
import com.example.logtest.log.context.ThreadLocalHolder;
import com.example.logtest.log.context.QueryCounter;
import com.example.logtest.log.context.id.AuthenticatedLogId;
import com.example.logtest.log.context.id.LogId;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLogInterceptor implements HandlerInterceptor {

    private static final int QUERY_COUNT_WARNING_STANDARD = 5;
    private static final int TOTAL_TIME_WARNING_STANDARD_MS = 2500;

    private static final List<String> swaggerUriList = Arrays.asList("/swagger-resources", "/v2/api-docs");


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (CorsUtils.isPreFlightRequest(request) || isErrorUri(request)) {
            return true;
        }

        ThreadLocalHolder.getThreadLocalQueryCounterHolder().set(new QueryCounter());

//        LogContext logContext = new LogContext(LogId.from(SecurityUtils....));

        String requestIdFromNginx = ((HttpServletRequest) request).getHeader("X-Request-ID");
        String logId = StringUtils.hasText(requestIdFromNginx) ?
                requestIdFromNginx.substring(0, 8)
                : "system-" + UUID.randomUUID().toString().substring(0, 8).replace("-", "");

        MDC.put("request_id", logId);

        LogContext logContext = new LogContext(LogId.from(java.util.Optional.empty(), logId)); // userId는 jwt validation 된 객체의 id
        ThreadLocalHolder.getThreadLocalLogContextHolder().set(logContext);

        RequestInfoLogData requestInfoLogData = new RequestInfoLogData(logContext.logId(), request);
        requestInfoLogData.put("Controller Method", handlerMethod((HandlerMethod) handler));
        log.info("[Web Request START] : [{}]", requestInfoLogData);

        return true;
    }

    private boolean isErrorUri(HttpServletRequest request) {
        return request.getRequestURI().equals("/error");
    }

    private String handlerMethod(HandlerMethod handler) {
        String className = handler.getMethod().getDeclaringClass().getSimpleName();
        String methodName = handler.getMethod().getName();
        return className + "." + methodName + "()";
    }

    // swagger가 붙으면 cors 요청이나 error처럼 해당 요청들도 같이 걸러야 swagger를 띄울 때 요청들은 로그에 남지 않는다.
    private boolean isSwaggerRequest(HttpServletRequest request, Object handler) {
        boolean handlerCheck = handler instanceof ParameterizableViewController // 뷰 기반의 요청을 처리하고 뷰를 렌더링 하는데 사용하는 클래스
                || handler instanceof ResourceHttpRequestHandler; // 정적 리소스 제공하는 클래스

        String requestURI = request.getRequestURI();

        boolean uriCheck = false;
        for (String swaggerUri : swaggerUriList) {
            if (requestURI.startsWith(swaggerUri)) {
                uriCheck = true;
                break;
            }
        }

        return handlerCheck || uriCheck;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (CorsUtils.isPreFlightRequest(request) || isErrorUri(request)) {
            return;
        }

        LogContext logContext = ThreadLocalHolder.getThreadLocalLogContextHolder().get();
        QueryCounter queryCounter = ThreadLocalHolder.getThreadLocalQueryCounterHolder().get();
        long totalTime = logContext.totalTakenTime();
        log.info("[Web Request END] : ID: {}, URI: {}, METHOD: {}, STATUS: {}, 쿼리 개수: {}, 요청 처리 시간: {}ms",
                logContext.logId(),
                request.getRequestURI(),
                request.getMethod(),
                response.getStatus(),
                queryCounter.count(),
                logContext.totalTakenTime()
        );
        logWarning(logContext, queryCounter, totalTime);

        threadLocalClear();
        MDC.clear();
    }

    private void logWarning(LogContext logContext, QueryCounter queryCounter, long totalTime) {
        if (queryCounter.count() >= QUERY_COUNT_WARNING_STANDARD) {
            log.warn("[{}] : 쿼리가 {}번 이상 실행되었습니다. (총 {}번)",
                    logContext.logId(),
                    QUERY_COUNT_WARNING_STANDARD,
                    queryCounter.count()
            );
        }
        if (totalTime >= TOTAL_TIME_WARNING_STANDARD_MS) {
            log.warn("[{}] : 요청을 처리하는데 {}ms 이상 소요되었습니다. (총 {}ms)",
                    logContext.logId(),
                    TOTAL_TIME_WARNING_STANDARD_MS,
                    totalTime
            );
        }
    }

    private void threadLocalClear() {
        ThreadLocalHolder.getThreadLocalLogContextHolder().remove();
        ThreadLocalHolder.getThreadLocalQueryCounterHolder().remove();
    }
}
