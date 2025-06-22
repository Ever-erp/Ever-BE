package com.example.autoever_1st.notice.service;

import com.example.autoever_1st.notice.dao.NoticeDao;
import com.example.autoever_1st.notice.dto.res.NoticeResDto;
import com.example.autoever_1st.notice.dto.res.PageResDto;
import com.example.autoever_1st.notice.entities.Member;
import com.example.autoever_1st.notice.entities.Notice;
import com.example.autoever_1st.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public PageResDto<NoticeResDto> getNotices(int page, int size, String type, String text) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "noticeId"));

        Page<Notice> noticePage;

        if (type != null && text != null && !text.isBlank()) {
            switch (type.toLowerCase()) {
                case "title" -> noticePage = noticeRepository.findByTitleContaining(text, pageable);
                case "content" -> noticePage = noticeRepository.findByContentsContaining(text, pageable);
                case "writer" -> noticePage = noticeRepository.findByWriterContaining(text, pageable);
                default -> noticePage = noticeRepository.findAll(pageable);
            }
        } else {
            noticePage = noticeRepository.findAll(pageable);
        }

        List<NoticeResDto> dtoList = noticePage.getContent().stream()
                .map(notice -> {
                    Member member = notice.getMember();
                    String writer = (member == null || !member.isActive()) ? "확인불가" : member.getName();
                    return new NoticeResDto(
                            notice.getNoticeId(),
                            notice.getType(),
                            notice.getTitle(),
                            writer,
                            notice.getContents(),
                            notice.getFile(),
                            notice.isPinned()
                    );
                })
                .toList();

        return new PageResDto<>(
                noticePage.getContent(),
                noticePage.getNumber(),
                noticePage.getSize(),
                noticePage.getTotalElements(),
                noticePage.getTotalPages(),
                noticePage.isLast()
        );
    }
        public List<NoticeResDto> findPinnedNotices() {
        return noticeDao.findPinnedNotices();
    }
    // 기타 CRUD는 생략 가능
}