package com.example.autoever_1st.notice.service;

import com.example.autoever_1st.notice.dto.req.NoticeWriteDto;
import com.example.autoever_1st.notice.dto.res.NoticeDto;
import com.example.autoever_1st.notice.constant.SearchType;
import com.example.autoever_1st.notice.constant.TargetRange;
import com.example.autoever_1st.notice.constant.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeService {
    // 공지 작성
    NoticeDto createNotice(NoticeWriteDto dto, Long memberId);
    // 공지 글 번호(noticeId) 검색
    NoticeDto getNotice(Long id);
    // 공지 전체 조회(페이징)
    Page<NoticeDto> getAllNotices(Pageable pageable);
    // 공지 키워드 조회 (제목/내용/작성자)
    Page<NoticeDto> searchNotices(SearchType searchType, String text, Pageable pageable);
    // 공지 유형 (공개범위/구분)으로 조회(AND, OR)
    Page<NoticeDto> searchByTargetRangeAndType(TargetRange targetRange, Type type, Pageable pageable);
    // 연/월로 해당하는 공지 전체 조회 (캘린더용)
    List<NoticeDto> getNoticesByYearAndMonth(int year, int month);
    // 공지 수정(PUT)
    NoticeDto updateNotice(Long id, NoticeWriteDto dto);
    // 공지 수정(PATCH)
    NoticeDto updateNoticePartial(Long id, NoticeWriteDto dto);
    // 공지 삭제
    void deleteNotice(Long id);
}
