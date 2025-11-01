package com.capstone.samadhi.config;

import java.util.List;

public class Whitelist {
    public static final List<String> SWAGGER_WHITELIST = List.of(
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-resources/**",
            "/webjars/**",
            "/configuration/ui",
            "/configuration/security",
            "/error",
            "/favicon.ico"
    );

    public static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/**",
            "/",
            "/auth/**",
            "/oauth2/**",
            "/login/oauth2/**",
            "/test/**",
            "/error"
    );
}
