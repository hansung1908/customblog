package com.hansung.customblog.repository;

import com.hansung.customblog.dto.response.NoticeListResponseDto;
import com.hansung.customblog.model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NoticeRepository  extends JpaRepository<Notice, Integer> {

    @Query(value = "SELECT * FROM notice ORDER BY createDate DESC LIMIT 1", nativeQuery = true)
    Optional<Notice> findLatestNotice();

    @Query("SELECT new com.hansung.customblog.dto.response.NoticeListResponseDto(n.noticeType, n.title, n.content) FROM Notice n")
    Page<NoticeListResponseDto> findNoticeList(Pageable pageable);

    @Query("SELECT new com.hansung.customblog.dto.response.NoticeListResponseDto(n.noticeType, n.title, n.content) FROM Notice n WHERE n.title LIKE %:noticeKeyword%")
    Page<NoticeListResponseDto> findNoticeByKeyword(@Param("noticeKeyword") String noticeKeyword, Pageable pageable);

    void deleteByTitle(String noticeTitle);
}
