package com.hansung.customblog.service;

import com.hansung.customblog.model.RoleType;
import com.hansung.customblog.model.User;
import com.hansung.customblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void save(User user) {
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);

        User newUser = new User.Builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(encPassword)
                .email(user.getEmail())
                .role(RoleType.USER)
                .provider(user.getProvider())
                .providerId(user.getProviderId())
                .createDate(user.getCreateDate())
                .build();

        userRepository.save(newUser);
    }

    @Transactional
    public void update(User user) {
        User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 실패");
        });

        if(persistance.getProvider() == null || persistance.getProvider().equals("")) {
            String rawPassword = user.getPassword();
            String encPassword = passwordEncoder.encode(rawPassword);
            persistance = persistance.updatePassword(encPassword);
        }

        persistance = persistance.updateEmail(user.getEmail());

        userRepository.save(persistance);
    }
}
