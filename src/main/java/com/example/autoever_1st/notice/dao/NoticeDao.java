package com.example.autoever_1st.notice.dao;

import com.example.autoever_1st.notice.dto.res.NoticeResDto;
import java.util.List;

public interface NoticeDao {
    List<NoticeResDto> findPinnedNotices();
}
