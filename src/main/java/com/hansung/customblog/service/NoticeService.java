package com.hansung.customblog.service;

import com.hansung.customblog.dto.request.NoticeSaveRequestDto;
import com.hansung.customblog.dto.response.NoticeListResponseDto;
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
        return noticeRepository.findLatestNotice().orElseThrow(() -> new IllegalArgumentException("마지막 공지를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public Page<NoticeListResponseDto> noticeList(Pageable pageable) {
        return noticeRepository.findNoticeList(pageable);
    }

    @Transactional(readOnly = true)
    public Page<NoticeListResponseDto> getNoticeListByKeyword(String noticeKeyword, Pageable pageable) {
        return noticeRepository.findNoticeByKeyword(noticeKeyword, pageable);
    }

    @Transactional
    public void delete(String noticeTitle) {
        noticeRepository.deleteByTitle(noticeTitle);
    }
}
