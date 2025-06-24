package com.example.autoever_1st.notice.dao.impl;

import com.example.autoever_1st.notice.dao.NoticeJdbcDao;
import com.example.autoever_1st.notice.dto.res.NoticeDto;
import com.example.autoever_1st.notice.model.SearchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeJdbcDaoImpl implements NoticeJdbcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public PageImpl<NoticeDto> searchByKeyword(SearchType type, String text, Pageable pageable) {
        String baseQuery = """
    SELECT\s
        n.notice_id AS noticeId,
        n.title,
        n.writer,
        n.contents,
        n.is_pinned AS isPinned,
        n.target_range AS targetRange,
        n.target_date AS targetDate,
        n.type
    FROM notice n
""";
        String whereClause = "";
        String paramValue = "%" + text + "%";
        // 글 유형 카테고리(enum) : search_title(제목), search_contents(내용), search_writer(작성자)
        switch (type) {
            case TITLE -> whereClause = "WHERE n.title LIKE ?";
            case CONTENTS -> whereClause = "WHERE n.contents LIKE ?";
            case WRITER -> whereClause = "WHERE n.writer LIKE ?";
        }

        String orderBy = " ORDER BY n.is_pinned DESC, n.notice_id DESC ";
        String limitOffset = " LIMIT ? OFFSET ? ";
        String finalQuery = baseQuery + whereClause + orderBy + limitOffset;
        String countQuery = """
    SELECT COUNT(*) FROM notice n\s
""" + whereClause;

        int total = jdbcTemplate.queryForObject(countQuery, Integer.class, paramValue);
        List<NoticeDto> content = jdbcTemplate.query(
                finalQuery,
                new Object[]{paramValue, pageable.getPageSize(), pageable.getOffset()},
                new BeanPropertyRowMapper<>(NoticeDto.class)
        );

        return new PageImpl<>(content, pageable, total);
    }
}
