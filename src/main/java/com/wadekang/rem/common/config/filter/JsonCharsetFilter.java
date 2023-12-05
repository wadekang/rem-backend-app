package com.wadekang.rem.common.config.filter;

import jakarta.servlet.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Response Content-Type을 application/json;charset=UTF-8으로 설정
 */
@Component
public class JsonCharsetFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        chain.doFilter(request, response);
    }
}
