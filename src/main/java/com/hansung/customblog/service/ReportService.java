package com.hansung.customblog.service;

import com.hansung.customblog.dto.request.NoticeSaveRequestDto;
import com.hansung.customblog.dto.request.ReportSaveRequestDto;
import com.hansung.customblog.model.Notice;
import com.hansung.customblog.model.Report;
import com.hansung.customblog.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Transactional
    public void save(ReportSaveRequestDto reportSaveRequestDto) {
        Report report = new Report.Builder()
                .targetBoardId(reportSaveRequestDto.getTargetBoardId())
                .targetBoardTitle(reportSaveRequestDto.getTargetBoardTitle())
                .reporterUserId(reportSaveRequestDto.getReporterUserId())
                .reporterUsername(reportSaveRequestDto.getReporterUsername())
                .reason(reportSaveRequestDto.getReason())
                .build();

        reportRepository.save(report);
    }
}
