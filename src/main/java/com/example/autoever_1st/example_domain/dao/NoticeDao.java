package com.example.autoever_1st.example_domain.dao;

import com.example.autoever_1st.example_domain.dto.res.NoticeResDto;
import java.util.List;

public interface NoticeDao {
    List<NoticeResDto> findPinnedNotices();
}
