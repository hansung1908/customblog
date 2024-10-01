package com.hansung.customblog.service;

import com.hansung.customblog.dto.request.UserSaveRequestDto;
import com.hansung.customblog.dto.request.UserUpdateRequestDto;
import com.hansung.customblog.model.RoleType;
import com.hansung.customblog.model.User;
import com.hansung.customblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        public void save(UserSaveRequestDto userSaveRequestDto) {
        String rawPassword = userSaveRequestDto.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);

        User newUser = new User.Builder()
                .username(userSaveRequestDto.getUsername())
                .password(encPassword)
                .email(userSaveRequestDto.getEmail())
                .role(RoleType.USER)
                .build();

        userRepository.save(newUser);
    }

    @Transactional
    public void update(UserUpdateRequestDto userUpdateRequestDto) {
        User tmpUser = userRepository.findById(userUpdateRequestDto.getId()).orElseThrow(() -> new IllegalArgumentException("회원 찾기 실패"));
        String updatePassword = tmpUser.getPassword();

        if(tmpUser.getProvider() == null || tmpUser.getProvider().equals("")) {
            String rawPassword = userUpdateRequestDto.getPassword();
            updatePassword = passwordEncoder.encode(rawPassword);
        }

        User updateUser = tmpUser.toBuilder()
                .password(updatePassword)
                .email(userUpdateRequestDto.getEmail())
                .build();

        userRepository.save(updateUser);
    }

    @Transactional
    public long checkName(String username) {
        return userRepository.existsByName(username);
    }

    @Transactional(readOnly = true) // 읽기 전용을 설정해 속도 올림
    public Page<User> userList(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true) // 읽기 전용을 설정해 속도 올림
    public Page<User> userListByKeyword(String userKeyword, Pageable pageable) {
        return userRepository.findUserByKeyword(userKeyword, pageable);
    }

    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
