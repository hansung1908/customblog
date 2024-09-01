package com.hansung.customblog.repository;

import com.hansung.customblog.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Modifying
    @Query(value = "UPDATE board SET count = count + 1 WHERE id = ?1", nativeQuery = true)
    void countUpById(int id);

    // 작성자와 키워드 값 비교는 서브 쿼리를 사용하여 일치하는 작성자 id와 게시물 작성자 id를 비교
    @Query("SELECT b FROM Board b WHERE b.title LIKE %:keyword% OR b.user.id IN (SELECT u.id FROM User u WHERE u.username LIKE %:keyword%)")
    Page<Board> findBoardByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
