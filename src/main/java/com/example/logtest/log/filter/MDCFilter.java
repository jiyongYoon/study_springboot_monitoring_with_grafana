package com.example.logtest.log.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

/**
 * Filter를 사용하면 http Request에 대해서는 컨트롤할 수 있지만, Springboot 단의 정보를 포함하지 않고 있어서
 * 좀 더 많은 정보를 담기위해 Filter가 아닌 Interceptor를 사용하는 것으로 변경해봄
 */
/*
@Component
class MDCFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String requestId = ((HttpServletRequest) request).getHeader("X-Request-ID");
        MDC.put("request_id",
                StringUtils.hasText(requestId) ?
                        requestId : "system-" + UUID.randomUUID().toString().replace("-", ""));
        chain.doFilter(request, response);
        MDC.clear(); // MDC를 설정해준 요청의 응답이 반환되기 전에 새로운 요청이 오게 되면 데이터가 남아있을 수 있기 때문에 clear해줌
    }
}
 */