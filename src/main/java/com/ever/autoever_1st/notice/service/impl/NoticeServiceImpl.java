package com.ever.autoever_1st.notice.service.impl;

import com.ever.autoever_1st.notice.constant.SearchType;
import com.ever.autoever_1st.notice.constant.TargetRange;
import com.ever.autoever_1st.notice.constant.Type;
import com.ever.autoever_1st.notice.dto.req.NoticeWriteDto;
import com.ever.autoever_1st.notice.dto.res.NoticeDto;
import com.ever.autoever_1st.notice.repository.NoticeRepository;
import com.ever.autoever_1st.auth.entities.Member;
import com.ever.autoever_1st.auth.repository.MemberRepository;
import com.ever.autoever_1st.notice.entities.Notice;
import com.ever.autoever_1st.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;


    // 공지 생성
    @Override @Transactional
    public NoticeDto createNotice(NoticeWriteDto dto, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
        Notice notice = new Notice();
        notice.setTitle(dto.getTitle());
        notice.setContents(dto.getContents());
        notice.setPinned(dto.getIsPinned());
        notice.setTargetRange(dto.getTargetRange());
        notice.setTargetDate(dto.getTargetDate());
        notice.setType(dto.getType());
        notice.setMember(member);
        notice.setWriter(member.getName());

        return NoticeDto.toDto(noticeRepository.save(notice));
    }

    // 공지 글 번호로 조회
    @Override @Transactional(readOnly = true)
    public NoticeDto getNotice(Long id, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지를 찾을 수 없습니다. : " + id));
        return NoticeDto.toDto(notice);
    }

    // 공지 전체 조회
    @Override @Transactional(readOnly = true)
    public Page<NoticeDto> getAllNotices(Pageable pageable, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
        return noticeRepository.findAll(pageable).map(NoticeDto::toDto);
    }

    // 공지 키워드로 조회
    @Override @Transactional(readOnly = true)
    public Page<NoticeDto> searchNotices(SearchType searchType, String text, Pageable pageable, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
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

    // 공지 전체 수정
    @Override @Transactional
    public NoticeDto updateNotice(Long id, NoticeWriteDto dto,Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지를 찾을 수 없습니다. : " + id));

        notice.setTargetRange(dto.getTargetRange());
        notice.setTitle(dto.getTitle());
        notice.setContents(dto.getContents());
        notice.setPinned(dto.getIsPinned());

        return NoticeDto.toDto(noticeRepository.save(notice));
    }

    // 공지 삭제
    @Override @Transactional
    public void deleteNotice(Long id,Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("공지를 찾을 수 없습니다. : " + id));

        noticeRepository.delete(notice);
    }
}
