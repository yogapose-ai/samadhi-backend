package com.capstone.samadhi.config;
import static  com.capstone.samadhi.config.Whitelist.*;

import com.capstone.samadhi.handler.CustomLogoutHandler;
import com.capstone.samadhi.handler.CustomLogoutSuccessHandler;
import com.capstone.samadhi.handler.FailedAuthenticationEntryPoint;
import com.capstone.samadhi.handler.OAuth2SuccessHandler;
import com.capstone.samadhi.security.jwt.JwtAuthenticationFilter;
import com.capstone.samadhi.security.jwt.JwtUtils;
import com.capstone.samadhi.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final DefaultOAuth2UserService oAuth2UserService;
    private final AuthService authService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final JwtUtils jwtUtils;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");


        httpSecurity
                .cors(cors -> cors.configurationSource(configurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(SWAGGER_WHITELIST.toArray(new String[0]))
                                .permitAll()
                                .requestMatchers(PUBLIC_ENDPOINTS.toArray(new String[0]))
                                .permitAll()
                                .requestMatchers("/api/test/**")
                                .hasRole("USER")
                                .anyRequest()
                                .authenticated()
                )
                .oauth2Login(oauth2 ->
                        oauth2
                                .authorizationEndpoint(endpoint -> endpoint.baseUri("/auth/oauth2"))
                                .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                                .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
                                .successHandler(oAuth2SuccessHandler)
                )
                .exceptionHandling(exceptionHandling ->
                    exceptionHandling.authenticationEntryPoint(new FailedAuthenticationEntryPoint())
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                    logout
                            .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                            .addLogoutHandler(new CustomLogoutHandler(jwtUtils, authService))
                            .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                            .deleteCookies("User-Token", "JSESSIONID")
                );

        return httpSecurity.build();
    }

    @Bean
    protected CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
