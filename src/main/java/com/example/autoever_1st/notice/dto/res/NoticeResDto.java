package com.example.autoever_1st.notice.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResDto {
    private Long noticeId;
    private String type;
    private String title;
    private String writer;
    private String contents;
    private String file;
    private boolean isPinned;

}