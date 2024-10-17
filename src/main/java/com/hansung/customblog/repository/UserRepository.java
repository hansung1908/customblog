package com.hansung.customblog.repository;

import com.hansung.customblog.dto.response.UserListResponseDto;
import com.hansung.customblog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// user 테이블에 pk는 integer
// 자동으로 bean 등록, 그래서 @Repository 생략
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    // 유저네일을 기존 db와 비교하여 유무 확인
    @Query(value = "SELECT EXISTS (SELECT 1 FROM user WHERE username = ?1)", nativeQuery = true)
    long existsByName(String username);

    @Query("SELECT new com.hansung.customblog.dto.response.UserListResponseDto(u.id, u.username, u.email, u.role, u.createDate) FROM User u")
    Page<UserListResponseDto> findUserList(Pageable pageable);

    @Query("SELECT new com.hansung.customblog.dto.response.UserListResponseDto(u.id, u.username, u.email, u.role, u.createDate)" +
            "FROM User u WHERE u.username LIKE %:userKeyword%")
    Page<UserListResponseDto> findUserByKeyword(@Param("userKeyword") String userKeyword, Pageable pageable);

    void deleteByUsername(String username);
}
