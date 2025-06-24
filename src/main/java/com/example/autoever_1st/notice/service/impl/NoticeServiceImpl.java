package com.example.autoever_1st.notice.service.impl;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.notice.dao.NoticeJdbcDao;
import com.example.autoever_1st.notice.dto.req.NoticeWriteDto;
import com.example.autoever_1st.notice.dto.res.NoticeDto;
import com.example.autoever_1st.notice.entities.Notice;
import com.example.autoever_1st.notice.model.SearchType;
import com.example.autoever_1st.notice.model.TargetRange;
import com.example.autoever_1st.notice.model.Type;
import com.example.autoever_1st.notice.repository.NoticeRepository;
import com.example.autoever_1st.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeRepository noticeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private NoticeJdbcDao noticeJdbcDao;

    // 공지 생성 - NoticeWriteDto와 memberId를 받아 Notice 엔티티 생성 후 저장
    @Transactional
    public NoticeDto createNotice(NoticeWriteDto dto, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

        Notice notice = new Notice();
        notice.setTitle(dto.getTitle());
        notice.setContents(dto.getContents());
        notice.setPinned(dto.getIsPinned());
        notice.setTargetRange(dto.getTargetRange());
        notice.setTargetDate(dto.getTargetDate());
        notice.setType(dto.getType());
        notice.setMember(member); // writer는 @PrePersist에서 자동 설정

        return toDto(noticeRepository.save(notice));
    }

    // 공지 글 번호로 조회
    @Transactional(readOnly = true)
    public NoticeDto getNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found: " + id));
        return toDto(notice);
    }

    // 공지 전체 조회
    @Transactional(readOnly = true)
    public Page<NoticeDto> getAllNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable).map(this::toDto);
    }

    // 공지 키워드로 조회
    @Transactional(readOnly = true)
    public Page<NoticeDto> searchNotices(SearchType searchType, String text, Pageable pageable) {
        return noticeJdbcDao.searchByKeyword(searchType, text, pageable);
    }

    // 공지 유형 (공개범위/구분)으로 조회(AND, OR)
    @Transactional(readOnly = true)
    public Page<NoticeDto> searchByTargetRangeAndType(TargetRange targetRange, Type type, Pageable pageable) {
        if (targetRange != TargetRange.ALL_TARGETRANGE && type != Type.ALL_TYPE) {
            return noticeRepository.findByTargetRangeAndType(targetRange, type, pageable).map(this::toDto);
        } else if (targetRange != TargetRange.ALL_TARGETRANGE) {
            return noticeRepository.findByTargetRange(targetRange, pageable).map(this::toDto);
        } else if (type != Type.ALL_TYPE) {
            return noticeRepository.findByType(type, pageable).map(this::toDto);
        } else {
            return noticeRepository.findAll(pageable).map(this::toDto);
        }
    }

    // 공지 전체 수정
    @Transactional
    public NoticeDto updateNotice(Long id, NoticeWriteDto dto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found: " + id));

        notice.setTargetRange(dto.getTargetRange());
        notice.setTitle(dto.getTitle());
        notice.setContents(dto.getContents());
        notice.setPinned(dto.getIsPinned());

        return toDto(noticeRepository.save(notice));
    }

    // 공지 부분 수정(인데 어차피 공지 전체 수정으로 다 될 듯)
    @Transactional
    public NoticeDto updateNoticePartial(Long id, NoticeWriteDto dto) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found: " + id));

        // 예시: null인 필드는 업데이트하지 않음
        if (dto.getTitle() != null) {
            notice.setTitle(dto.getTitle());
        }
        if (dto.getContents() != null) {
            notice.setContents(dto.getContents());
        }
        if (dto.getIsPinned() != null) {
            notice.setPinned(dto.getIsPinned());
        }
        if (dto.getTargetRange() != null) {
            notice.setTargetRange(dto.getTargetRange());
        }
        if (dto.getTargetDate() != null) {
            notice.setTargetDate(dto.getTargetDate());
        }
        if (dto.getType() != null) {
            notice.setType(dto.getType());
        }

        Notice updated = noticeRepository.save(notice);

        return new NoticeDto(
                updated.getNoticeId(),
                updated.getTitle(),
                updated.getWriter(),
                updated.getContents(),
                updated.isPinned(),
                updated.getTargetRange(),
                updated.getTargetDate(),
                updated.getType()
        );
    }

    // 공지 삭제
    @Transactional
    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    // 공지 조회 DTO 변환
    public NoticeDto toDto(Notice notice) {
        String writerName = "(없음)";
        if (notice.getMember() == null) {
            // 멤버 정보가 아예 없는 경우 (탈퇴 또는 삭제)
            writerName = "(삭제됨)";
        } else if (!notice.getMember().isActive()) {
            // 멤버는 있지만 비활성화 상태인 경우
            writerName = "(비활성화)";
        } else {
            // 정상 활성화 회원인 경우
            writerName = notice.getWriter();  // 또는 notice.getMember().getName();
        }
        return NoticeDto.builder()
                .noticeId(notice.getNoticeId())
                .title(notice.getTitle())
                .writer(writerName)
                .contents(notice.getContents())
                .isPinned(notice.isPinned())
                .targetRange(notice.getTargetRange())
                .targetDate(notice.getTargetDate())
                .type(notice.getType())
                .build();
    }
}
