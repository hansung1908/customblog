package com.hansung.customblog.config.oauth;

import com.hansung.customblog.config.auth.PrincipalDetail;
import com.hansung.customblog.config.oauth.provider.GoogleUserInfo;
import com.hansung.customblog.config.oauth.provider.KakaoUserInfo;
import com.hansung.customblog.config.oauth.provider.NaverUserInfo;
import com.hansung.customblog.config.oauth.provider.OAuth2UserInfo;
import com.hansung.customblog.model.RoleType;
import com.hansung.customblog.model.User;
import com.hansung.customblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Value("${hansung.key}")
    private String key;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    // userRequest 데이터에 대한 후처리 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes());
        } else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else {
            System.out.println("구글, 카카오, 네이버만 지원");
        }

        // 자동 회원가입
        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId;
        String password = bCryptPasswordEncoder.encode(key);
        String email = oAuth2UserInfo.getEmail();

        User userEntity = userRepository.findByUsername(username).orElseGet(() -> new User.Builder().defualt().build());

        if(userEntity.getUsername() == null) {
            System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다.");

            userEntity = new User.Builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .provider(provider)
                    .providerId(providerId)
                    .role(RoleType.USER)
                    .build();

            userRepository.save(userEntity);
        }

        return new PrincipalDetail(userEntity, oAuth2User.getAttributes());
    }
}
