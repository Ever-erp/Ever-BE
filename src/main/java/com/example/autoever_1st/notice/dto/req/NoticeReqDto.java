package com.example.autoever_1st.notice.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeReqDto {
    private String type;
    private String title;
    private String writer;
    private String contents;
    private String file;
    private boolean isPinned;
}