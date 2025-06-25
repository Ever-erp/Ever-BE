package com.example.autoever_1st.notice.dao;

import com.example.autoever_1st.notice.constant.SearchType;
import com.example.autoever_1st.notice.dto.res.NoticeDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoticeJdbcDao {
    // 글 유형 카테고리(enum) : search_title(제목), search_contents(내용), search_writer(작성자)
    PageImpl<NoticeDto> searchByKeyword(SearchType type, String text, Pageable pageable);
    // 날짜 정보만 담은 dto
    List<NoticeDto> findNoticesByYearAndMonth(int year, int month);
}
