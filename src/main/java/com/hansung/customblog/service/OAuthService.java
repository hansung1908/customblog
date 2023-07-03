package com.hansung.customblog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hansung.customblog.model.KakaoProfile;
import com.hansung.customblog.model.OAuthToken;
import com.hansung.customblog.model.User;
import com.hansung.customblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuthService {

    @Value("${hansung.key}")
    private String key;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public ResponseEntity<String> token(ResponseEntity<String> response) throws JsonProcessingException {
        ObjectMapper ob = new ObjectMapper();
        OAuthToken oauthToken = ob.readValue(response.getBody(), OAuthToken.class);

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oauthToken.getAccess_token());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> profileResponse = rt.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoProfileRequest, String.class);

        KakaoProfile kakaoProfile = ob.readValue(profileResponse.getBody(), KakaoProfile.class);

        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakao_account().getEmail() + " " + kakaoProfile.getId())
                .password(key)
                .email(kakaoProfile.getKakao_account().getEmail())
                .build();

        // 가입자 혹은 비가입자 체크
        User originUser = userRepository.findByUsername(kakaoUser.getUsername()).orElseGet(() -> {
            return new User();
        });

        // 비가입자면 회원가입
        if(originUser.getUsername() == null) {
            System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다.");
            userService.save(kakaoUser);
        }

        // 로그인 처리
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), key));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return profileResponse;
    }
}
