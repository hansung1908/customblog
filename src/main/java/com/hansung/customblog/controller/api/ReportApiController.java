package com.hansung.customblog.controller.api;

import com.hansung.customblog.dto.request.ReportSaveRequestDto;
import com.hansung.customblog.dto.response.ResponseDto;
import com.hansung.customblog.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportApiController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/api/report")
    public ResponseDto<String> save(@RequestBody ReportSaveRequestDto reportSaveRequestDto, BindingResult bindingResult) {
        reportService.save(reportSaveRequestDto);
        return new ResponseDto<String>(HttpStatus.OK.value(), "리포트 제출이 완료되었습니다.");
    }
}
