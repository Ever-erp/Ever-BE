package com.example.autoever_1st.notice.dao.impl;

import com.example.autoever_1st.notice.dao.NoticeDao;
import com.example.autoever_1st.notice.dto.res.NoticeResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoticeDaoImpl implements NoticeDao {

    private final JdbcTemplate jdbc;

    @Override
    public List<NoticeResDto> findPinnedNotices() {
        String sql = "SELECT * FROM notice WHERE is_pinned = true";
        return jdbc.query(sql, (rs, rowNum) -> {
            NoticeResDto dto = new NoticeResDto();
            dto.setNoticeId(rs.getLong("notice_id"));
            dto.setType(rs.getString("type"));
            dto.setTitle(rs.getString("title"));
            dto.setWriter(rs.getString("writer"));
            dto.setContents(rs.getString("contents"));
            dto.setFile(rs.getString("file"));
            dto.setPinned(rs.getBoolean("is_pinned"));
            return dto;
        });
    }
}