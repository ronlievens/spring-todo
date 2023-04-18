package com.github.ronlievens.spring.todo.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter extends OncePerRequestFilter {

    private static final String HEADER_IP = "IP";
    private static final String REQUEST_ID = "RID";
    private static final String USER_IP = "X-Forwarded-For";

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        MDC.put(HEADER_IP, request.getHeader(USER_IP));
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
        log.trace("Start logging for {} {}", request.getMethod(), request.getRequestURI());
        filterChain.doFilter(request, response);
        log.trace("End logging for {} {}", request.getMethod(), request.getRequestURI());
        MDC.clear();
    }
}
