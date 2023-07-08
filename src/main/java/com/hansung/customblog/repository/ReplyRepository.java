package com.hansung.customblog.repository;

import com.hansung.customblog.dto.ReplySaveRequestDto;
import com.hansung.customblog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES(?1, ?2, ?3, now())", nativeQuery = true)
    void mSave(int userId, int boardId, String content);
}
