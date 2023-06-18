package com.hansung.customblog.service;

import com.hansung.customblog.domain.User;
import com.hansung.customblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    public User find(String name) {
        User user = userRepository.findByUsername(name).orElseGet(() -> {return new User();});
        return user;
    }

    @Transactional
    public void join(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void update(User user) {

        // 영속화하여 db데이터를 가져와 수정
        User persistence = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 실패");
        });

        // 영속화 데이터를 수정하면 알아서 트랜잭션 종료후 update 전송
        persistence.setPassword(user.getPassword());
        persistence.setEmail(user.getEmail());
    }
}
