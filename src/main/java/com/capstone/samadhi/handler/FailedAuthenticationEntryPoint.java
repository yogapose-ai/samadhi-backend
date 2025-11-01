package com.capstone.samadhi.handler;
import static  com.capstone.samadhi.config.Whitelist.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

public class FailedAuthenticationEntryPoint  implements AuthenticationEntryPoint {
    private final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String uri = request.getRequestURI();
        boolean isSwaggerRequest = SWAGGER_WHITELIST.stream().anyMatch(pattern -> matcher.match(pattern, uri));

        if(isSwaggerRequest) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("{\"code\": \"NP\", \"message\": \"No Permission.\"}");
    }
}
