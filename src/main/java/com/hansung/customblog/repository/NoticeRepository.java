package com.hansung.customblog.repository;

import com.hansung.customblog.model.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository  extends JpaRepository<Notice, Integer> {

    @Query(value = "SELECT * FROM notice ORDER BY createDate DESC LIMIT 1", nativeQuery = true)
    Notice findLatestNotice();

    @Query("SELECT n FROM Notice n WHERE n.title LIKE %:noticeKeyword%")
    Page<Notice> findNoticeByKeyword(@Param("noticeKeyword") String noticeKeyword, Pageable pageable);
}
