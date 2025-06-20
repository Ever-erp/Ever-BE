package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NoticeResDto {
    private Long noticeId;
    private String type;
    private String title;
    private String writer;
    private String contents;
    private String file;
    private boolean isPinned;
}