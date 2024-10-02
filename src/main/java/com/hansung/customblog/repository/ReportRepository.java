package com.hansung.customblog.repository;

import com.hansung.customblog.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
