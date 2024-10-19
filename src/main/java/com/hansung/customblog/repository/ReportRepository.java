package com.hansung.customblog.repository;

import com.hansung.customblog.dto.response.NoticeListResponseDto;
import com.hansung.customblog.dto.response.ReportListResponseDto;
import com.hansung.customblog.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("SELECT new com.hansung.customblog.dto.response.ReportListResponseDto(r.targetBoardId, r.targetBoardTitle, r.reporterUserId, r.reporterUsername, r.reason) FROM Report r")
    Page<ReportListResponseDto> findReportList(Pageable pageable);

    @Query("SELECT new com.hansung.customblog.dto.response.ReportListResponseDto(r.targetBoardId, r.targetBoardTitle, r.reporterUserId, r.reporterUsername, r.reason) FROM Report r " +
            "WHERE r.targetBoardTitle LIKE %:reportKeyword% OR r.reporterUsername LIKE %:reportKeyword%")
    Page<ReportListResponseDto> findReportByKeyword(@Param("reportKeyword") String reportKeyword, Pageable pageable);
}
