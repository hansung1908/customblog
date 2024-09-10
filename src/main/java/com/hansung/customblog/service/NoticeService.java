package com.hansung.customblog.service;

import com.hansung.customblog.dto.request.NoticeSaveRequestDto;
import com.hansung.customblog.model.Notice;
import com.hansung.customblog.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Transactional
    public void save(NoticeSaveRequestDto noticeSaveRequestDto){
        Notice notice = new Notice.Builder()
                .noticeType(noticeSaveRequestDto.getNoticeType())
                .title(noticeSaveRequestDto.getTitle())
                .content(noticeSaveRequestDto.getContent())
                .build();

        noticeRepository.save(notice);
    }

    @Transactional
    public Notice findLatestNotice() {
        return noticeRepository.findLatestNotice();
    }
}
