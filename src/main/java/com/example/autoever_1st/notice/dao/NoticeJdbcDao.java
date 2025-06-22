package com.example.autoever_1st.notice.dao;

import com.example.autoever_1st.notice.dto.res.NoticeDto;
import com.example.autoever_1st.notice.model.SearchType;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface NoticeJdbcDao {
    PageImpl<NoticeDto> searchByKeyword(SearchType type, String text, Pageable pageable);
    // 글 유형 카테고리(enum) : search_title(제목), search_contents(내용), search_writer(작성자)
}
