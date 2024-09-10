package com.hansung.customblog.repository;

import com.hansung.customblog.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoticeRepository  extends JpaRepository<Notice, Integer> {

    @Query(value = "SELECT * FROM notice ORDER BY createDate DESC LIMIT 1", nativeQuery = true)
    Notice findLatestNotice();
}
