package com.hansung.customblog.repository;

import com.hansung.customblog.dto.response.BoardDetailResponseDto;
import com.hansung.customblog.dto.response.BoardListResponseDto;
import com.hansung.customblog.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Modifying
    @Query(value = "UPDATE board SET count = count + 1 WHERE id = ?1", nativeQuery = true)
    void countUpById(int id);

    // 작성자와 키워드 값 비교는 서브 쿼리를 사용하여 일치하는 작성자 id와 게시물 작성자 id를 비교
    @Query("SELECT new com.hansung.customblog.dto.response.BoardListResponseDto(b.id, b.title, b.count, u.username)" +
            "FROM Board b JOIN b.user u WHERE b.title LIKE %:boardKeyword% OR b.user.id IN (SELECT u.id FROM User u WHERE u.username LIKE %:boardKeyword%)")
    Page<BoardListResponseDto> findBoardListByKeyword(@Param("boardKeyword") String boardKeyword, Pageable pageable);

    @Query("SELECT new com.hansung.customblog.dto.response.BoardListResponseDto(b.id, b.title, b.count, u.username) FROM Board b JOIN b.user u")
    Page<BoardListResponseDto> findBoardList(Pageable pageable);

    @Query("SELECT new com.hansung.customblog.dto.response.BoardDetailResponseDto(b.id, b.title, b.content, b.count, u.id, u.username)" +
            "FROM Board b JOIN b.user u WHERE b.id = :boardId")
    Optional<BoardDetailResponseDto> findBoardDetail(@Param("boardId") int id);
}
