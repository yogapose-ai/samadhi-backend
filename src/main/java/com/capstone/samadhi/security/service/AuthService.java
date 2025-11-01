package com.capstone.samadhi.security.service;

import com.capstone.samadhi.exception.LoginExpirationException;
import com.capstone.samadhi.security.jwt.JwtUtils;
import com.capstone.samadhi.security.repo.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    /**
     * 구글 로그아웃
     * @param token 구글로부터 발급받은 토큰
     */
    public void googleLogout(String token) {
        String tokenInfoUrl = "https://oauth2.googleapis.com/tokeninfo?access_token=" + token;
        try {
            URL url = new URL(tokenInfoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int respCode = conn.getResponseCode();
            log.info("구글 로그아웃 결과: {}", respCode);
        } catch (IOException e) {
            log.error("구글 로그아웃 중 에러 발생");
        }
    }


    /**
     * 구글 회원탈퇴
     * @param id 사용자 아이디
     * @param request 요청
     */
    public void googleUnlink(String id, HttpServletRequest request) {
        String tokenInfoUrl = "https://oauth2.googleapis.com/revoke";
        String accessToken = jwtUtils.parseBearerToken(request);

        if (accessToken == null) {
            throw LoginExpirationException.EXCEPTION;
        }

        try {
            URL url = new URL(tokenInfoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            String postData = "token=" + accessToken;
            byte[] postDataBytes = postData.getBytes("UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postDataBytes);
            }

            int responseCode = conn.getResponseCode();
            log.info("구글 로그인 탈퇴 결과: {}", responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
        } catch (IOException e) {
            log.error("구글 회원탈퇴 중 에러 발생: {}", e.getMessage());
        }
    }
}
