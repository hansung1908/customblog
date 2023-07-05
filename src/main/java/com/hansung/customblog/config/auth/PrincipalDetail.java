package com.hansung.customblog.config.auth;

import com.hansung.customblog.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetail implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    // 일반 로그인
    public PrincipalDetail(User user) {
        this.user = user;
    }

    // OAuth 로그인
    public PrincipalDetail(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료 유무(true: 만료 x)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 락 유무(true: 락 x)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 유무(true: 만료 x)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 유무(true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 권한 등록
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> "ROLE_" + user.getRole());

        return collection;
    }

    @Override
    public String getName() {
        return null;
    }
}
