package com.hansung.customblog.service;

import com.hansung.customblog.dto.request.NoticeSaveRequestDto;
import com.hansung.customblog.model.Notice;
import com.hansung.customblog.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Transactional(readOnly = true)
    public Notice findLatestNotice() {
        return noticeRepository.findLatestNotice();
    }

    @Transactional(readOnly = true)
    public Page<Notice> noticeList(Pageable pageable) {
        return noticeRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Notice> noticeListByKeyword(String noticeKeyword, Pageable pageable) {
        return noticeRepository.findNoticeByKeyword(noticeKeyword, pageable);
    }
}
