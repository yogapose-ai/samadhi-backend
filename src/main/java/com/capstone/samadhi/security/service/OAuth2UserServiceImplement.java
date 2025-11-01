package com.capstone.samadhi.security.service;

import com.capstone.samadhi.security.entity.CustomOAuth2User;
import com.capstone.samadhi.security.entity.User;
import com.capstone.samadhi.security.repo.UserRepository;
import com.capstone.samadhi.security.service.response.GoogleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final long ACCESS_TOKEN_EXPIRE_TIME = 3600*1000*3;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);

        String oauth2ClientName = request.getClientRegistration().getClientName();
        String oAuthToken = request.getAccessToken().getTokenValue();

        try{
            log.info(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }

        GoogleResponse response = new GoogleResponse(oAuth2User.getAttributes());
        String userId = response.getProviderId();
        String profile = response.getProfilePath();
        userRepository.save(new User(userId, profile));
        return new CustomOAuth2User(userId);
    }
}
