package com.example.autoever_1st.notice.service;

import com.example.autoever_1st.notice.constant.SearchType;
import com.example.autoever_1st.notice.constant.TargetRange;
import com.example.autoever_1st.notice.constant.Type;
import com.example.autoever_1st.notice.dto.req.NoticeWriteDto;
import com.example.autoever_1st.notice.dto.res.NoticeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface NoticeService {
    // 공지 작성
    NoticeDto createNotice(NoticeWriteDto dto, Authentication authentication);
    // 공지 글 번호(Id) 검색
    NoticeDto getNotice(Long id,Authentication authentication);
    // 공지 전체 조회(페이징)
    Page<NoticeDto> getAllNotices(Pageable pageable, Authentication authentication);
    // 공지 키워드 조회 (제목/내용/작성자)
    Page<NoticeDto> searchNotices(SearchType searchType, String text, Pageable pageable, Authentication authentication);
    // 공지 유형 (공개범위/구분)으로 조회(AND, OR)
    Page<NoticeDto> searchByTargetRangeAndType(TargetRange targetRange, Type type, Pageable pageable, Authentication authentication);
    // 공지 전체 수정(PUT)
    NoticeDto updateNotice(Long id, NoticeWriteDto dto,Authentication authentication);
    // 공지 삭제
    void deleteNotice(Long id,Authentication authentication);
}
