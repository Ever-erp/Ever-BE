package com.example.autoever_1st.notice.service;

import com.example.autoever_1st.notice.dao.NoticeDao;
import com.example.autoever_1st.notice.dto.res.NoticeResDto;
import com.example.autoever_1st.notice.entities.Notice;
import com.example.autoever_1st.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final NoticeDao noticeDao;

    public NoticeResDto findById(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found"));
        NoticeResDto dto = new NoticeResDto();
        dto.setNoticeId(notice.getNoticeId());
        dto.setType(notice.getType());
        dto.setTitle(notice.getTitle());
        dto.setWriter(notice.getWriter());
        dto.setContents(notice.getContents());
        dto.setFile(notice.getFile());
        dto.setPinned(notice.isPinned());
        return dto;
    }

    public List<NoticeResDto> findPinnedNotices() {
        return noticeDao.findPinnedNotices();
    }

    // 기타 CRUD는 생략 가능
}