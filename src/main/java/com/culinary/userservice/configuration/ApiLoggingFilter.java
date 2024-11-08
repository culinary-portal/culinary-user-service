package com.culinary.userservice.configuration;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ApiLoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String origin = httpRequest.getHeader(HttpHeaders.ORIGIN);
        log.info("API call: {} {} from origin: {}", httpRequest.getMethod(), httpRequest.getRequestURI(), origin);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}