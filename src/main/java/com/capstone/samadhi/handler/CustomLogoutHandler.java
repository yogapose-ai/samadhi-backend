package com.capstone.samadhi.handler;

import com.capstone.samadhi.security.jwt.JwtUtils;
import com.capstone.samadhi.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomLogoutHandler implements LogoutHandler {
    private final JwtUtils jwtUtils;
    private final AuthService authService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = jwtUtils.parseBearerToken(request);
        String userId = jwtUtils.validate(token);
        long expiration = jwtUtils.getRemainTime(token);

        authService.googleLogout(token);

        if(request.getSession(false) != null) {
            request.getSession().invalidate();
        }
    }
}
