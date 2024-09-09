package com.hansung.customblog.repository;

import com.hansung.customblog.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository  extends JpaRepository<Notice, Integer> {
}
