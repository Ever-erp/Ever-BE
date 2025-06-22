package com.example.autoever_1st.notice.dto.req;

import com.example.autoever_1st.notice.model.TargetRange;
import com.example.autoever_1st.notice.model.Type;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class NoticeWriteDto {            // 게시물 작성 DTO
    private String title;              // 제목
    private String contents;           // 내용
    private Boolean isPinned;          // 최상단 고정 여부
    private TargetRange targetRange;   // 공개 범위 : 전체/반(웹앱/스마트팩토리/SW임베디드/IT보안/클라우드)
    private LocalDate targetDate;      // 공개 기한
    private Type type;                 // 글 유형 (공지/설문)
}