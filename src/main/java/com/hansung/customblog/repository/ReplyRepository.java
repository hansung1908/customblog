package com.hansung.customblog.repository;

import com.hansung.customblog.dto.response.ReplyResponseDto;
import com.hansung.customblog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
    void replySave(int userId, int boardId, String content);

    @Query("SELECT new com.hansung.customblog.dto.response.ReplyResponseDto(r.id, r.content, ru.id, ru.username) " +
            "FROM Reply r JOIN r.user ru WHERE r.board.id = :boardId")
    List<ReplyResponseDto> findReplyListByBoardId(@Param("boardId") int id);
}
