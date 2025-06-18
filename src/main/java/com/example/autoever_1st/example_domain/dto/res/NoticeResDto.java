package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter // @NoArgsConstructor => 가짜 데이터 위해 비활성화 (실제 데이터는 생성자 지우기)
public class NoticeResDto {
    private int notice_id;       // 글 번호(index)
    private String type;         // 글 구분
    private String title;        // 제목
    private String writer;       // 작성자
    private String contents;    // 내용
    private String file;         // 첨부 파일
    private String image;        // 첨부 이미지
    private boolean isPin;       // 최상단 공지 여부
}
