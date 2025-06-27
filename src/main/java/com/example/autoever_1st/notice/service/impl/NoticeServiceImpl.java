package com.example.autoever_1st.notice.service.impl;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.notice.constant.SearchType;
import com.example.autoever_1st.notice.constant.TargetRange;
import com.example.autoever_1st.notice.constant.Type;
import com.example.autoever_1st.notice.dto.req.NoticeWriteDto;
import com.example.autoever_1st.notice.dto.res.NoticeDto;
import com.example.autoever_1st.notice.entities.Notice;
import com.example.autoever_1st.notice.repository.NoticeRepository;
import com.example.autoever_1st.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;


    // 공지 생성 - NoticeWriteDto와 memberId를 받아 Notice 엔티티 생성 후 저장
    @Override @Transactional
    public NoticeDto createNotice(NoticeWriteDto dto, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberEmail));
//        if (member.getPosition().getRole().equals( "(* 해당하는 role 값의 문자열 *)" )) {
//                throw new SecurityException("관리자만 공지를 작성할 수 있습니다.");
//            }
        Notice notice = new Notice();
        notice.setTitle(dto.getTitle());
        notice.setContents(dto.getContents());
        notice.setPinned(dto.getIsPinned());
        notice.setTargetRange(dto.getTargetRange());
        notice.setTargetDate(dto.getTargetDate());
        notice.setType(dto.getType());
        notice.setMember(member); // writer는 @PrePersist에서 자동 설정

        return NoticeDto.toDto(noticeRepository.save(notice));
    }

    // 공지 글 번호로 조회
    @Override @Transactional(readOnly = true)
    public NoticeDto getNotice(Long id, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberEmail));
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found: " + id));
        return NoticeDto.toDto(noticeRepository.save(notice));
    }

    // 공지 전체 조회
    @Override @Transactional(readOnly = true)
    public Page<NoticeDto> getAllNotices(Pageable pageable, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberEmail));
        return noticeRepository.findAll(pageable).map(NoticeDto::toDto);
    }

    // 공지 키워드로 조회
    @Override @Transactional(readOnly = true)
    public Page<NoticeDto> searchNotices(SearchType searchType, String text, Pageable pageable, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberEmail));
        // ── SearchType 에 따라 다른 JPA 쿼리 호출
        Page<Notice> page = switch (searchType) {
            case TITLE    -> noticeRepository
                    .findByTitleContainingIgnoreCaseOrderByIsPinnedDescIdDesc(text, pageable);
            case CONTENTS -> noticeRepository
                    .findByContentsContainingIgnoreCaseOrderByIsPinnedDescIdDesc(text, pageable);
            case WRITER   -> noticeRepository
                    .findByWriterContainingIgnoreCaseOrderByIsPinnedDescIdDesc(text, pageable);
        };

        // ── 엔티티 → DTO 변환 뒤 그대로 반환
        return page.map(NoticeDto::toDto);
    }

//    private NoticeDto toDto(Notice n) {
//        return NoticeDto.builder()
//                .id(n.getId())
//                .title(n.getTitle())
//                .contents(n.getContents())
//                .writer(n.getWriter())
//                .isPinned(n.isPinned())
//                .targetRange(n.getTargetRange())
//                .targetDate(n.getTargetDate())
//                .type(n.getType())
//                .registedAt(n.getRegistedAt())
//                .build();
//    }



    // 특정 연도+월 공지 조회 메서드
//    @Override @Transactional(readOnly = true)
//    public List<NoticeDto> getNoticesByYearAndMonth(int year, int month, Authentication authentication) {
//        String memberEmail = authentication.getName();
//        Member member = memberRepository.findByEmail(memberEmail)
//                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberEmail));
//
//        LocalDate startDate = LocalDate.of(year, month, 1);
//        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
//        List<Notice> notices = noticeRepository.findByTargetDateIsNotNullAndTargetDateBetweenOrderByIsPinnedDescRegistedAtDesc(startDate, endDate);
//        return notices.stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }

    // 공지 유형 (공개범위/구분)으로 조회(AND, OR)
    @Override @Transactional(readOnly = true)
    public Page<NoticeDto> searchByTargetRangeAndType(TargetRange targetRange, Type type, Pageable pageable, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberEmail));

        if (targetRange != TargetRange.ALL_TARGETRANGE && type != Type.ALL_TYPE) {
            return noticeRepository.findByTargetRangeAndType(targetRange, type, pageable).map(NoticeDto::toDto);
        } else if (targetRange != TargetRange.ALL_TARGETRANGE) {
            return noticeRepository.findByTargetRange(targetRange, pageable).map(NoticeDto::toDto);
        } else if (type != Type.ALL_TYPE) {
            return noticeRepository.findByType(type, pageable).map(NoticeDto::toDto);
        } else {
            return noticeRepository.findAll(pageable).map(NoticeDto::toDto);
        }
    }

    // 공지 전체 수정
    @Override @Transactional
    public NoticeDto updateNotice(Long id, NoticeWriteDto dto,Authentication authentication) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found: " + id));
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberEmail));

        notice.setTargetRange(dto.getTargetRange());
        notice.setTitle(dto.getTitle());
        notice.setContents(dto.getContents());
        notice.setPinned(dto.getIsPinned());

        return NoticeDto.toDto(noticeRepository.save(notice));
    }

    // 공지 삭제
    @Override @Transactional
    public void deleteNotice(Long id,Authentication authentication) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found: " + id));
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberEmail));
        noticeRepository.delete(notice);
    }

    // 공지 조회 DTO 변환
//    public NoticeDto toDto(Notice notice) {
//        String writerName = null;
//        if (notice.getMember() == null) {
//            // 멤버 정보가 아예 없는 경우 (탈퇴 또는 삭제)
//            writerName = "(삭제됨)";
//        } else if (!notice.getMember().isActive()) {
//            // 멤버는 있지만 비활성화 상태인 경우
//            writerName = "(비활성화)";
//        } else {
//            // 정상 활성화 회원인 경우
//            writerName = notice.getWriter();
//        }
//        return NoticeDto.builder()
//                .id(notice.getId())
//                .title(notice.getTitle())
//                .writer(writerName)
//                .contents(notice.getContents())
//                .isPinned(notice.isPinned())
//                .targetRange(notice.getTargetRange())
//                .targetDate(notice.getTargetDate())
//                .type(notice.getType())
//                .build();
//    }

    // 공지 날짜 표시 설정 저장
    @Transactional
    public Notice saveNotice(NoticeDto dto) {
        LocalDate targetDate = dto.getTargetDate();
        LocalDateTime now = LocalDateTime.now();

        if (targetDate == null) {
            // 작성일 날짜만 (시간 제외)
            targetDate = now.toLocalDate();
        }

        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .contents(dto.getContents())
                .isPinned(dto.isPinned())
                .targetRange(dto.getTargetRange())
                .targetDate(targetDate)  // 앞에서 null 처리한 값
                .type(dto.getType())
                .build();

        return noticeRepository.save(notice);
    }



}
