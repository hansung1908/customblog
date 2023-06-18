package com.hansung.customblog.repository;

import com.hansung.customblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// jpa 사용으로 @Repository 생략
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
