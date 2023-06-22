package com.hansung.customblog.repository;

import com.hansung.customblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// user 테이블에 pk는 integer
// 자동으로 bean 등록, 그래서 @Repository 생략
public interface UserRepository extends JpaRepository<User, Integer> {

}
