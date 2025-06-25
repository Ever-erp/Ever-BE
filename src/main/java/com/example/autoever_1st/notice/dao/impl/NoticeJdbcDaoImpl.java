package com.example.autoever_1st.notice.dao.impl;

import com.example.autoever_1st.notice.constant.SearchType;
import com.example.autoever_1st.notice.constant.TargetRange;
import com.example.autoever_1st.notice.constant.Type;
import com.example.autoever_1st.notice.dao.NoticeJdbcDao;
import com.example.autoever_1st.notice.dto.res.NoticeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoticeJdbcDaoImpl implements NoticeJdbcDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void NoticeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

    @Override
    public List<NoticeDto> findNoticesByYearAndMonth(int year, int month) {
        String sql = """
            SELECT id, title, contents, writer, registed_at, target_date, is_pinned, target_range, type
            FROM notice
            WHERE
                (target_date IS NOT NULL AND YEAR(target_date) = ? AND MONTH(target_date) = ?)
                OR
                (target_date IS NULL AND YEAR(registed_at) = ? AND MONTH(registed_at) = ?)
            ORDER BY is_pinned DESC, registed_at DESC
        """;

        return jdbcTemplate.query(sql,
                new Object[]{year, month, year, month},
                (rs, rowNum) -> NoticeDto.builder()
                        .id(rs.getLong("id"))
                        .title(rs.getString("title"))
                        .contents(rs.getString("contents"))
                        .writer(rs.getString("writer"))
                        .registedAt(rs.getTimestamp("registed_at").toLocalDateTime())
                        .targetDate(rs.getDate("target_date") != null ? rs.getDate("target_date").toLocalDate() : null)
                        .isPinned(rs.getBoolean("is_pinned"))
                        .targetRange(TargetRange.valueOf(rs.getString("target_range")))
                        .type(Type.valueOf(rs.getString("type")))
                        .build()
        );
    }
}