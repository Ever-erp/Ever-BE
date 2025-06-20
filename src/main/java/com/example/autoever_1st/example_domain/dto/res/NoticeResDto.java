package com.example.autoever_1st.example_domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResDto {
    private Long noticeId;       // 글 번호(index)
    private String type;         // 글 구분
    private String title;        // 제목
    private String writer;       // 작성자
    private String contents;     // 내용
    private String file;         // 첨부 파일
    private boolean isPin;       // 최상단 공지 여부
}
