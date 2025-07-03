package com.ever.autoever_1st.notice.dto.res;

import com.ever.autoever_1st.notice.constant.TargetRange;
import com.ever.autoever_1st.notice.constant.Type;
import com.ever.autoever_1st.notice.entities.Notice;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class NoticeDto {                // 게시물 "조회" DTO (요청/응답)
    private Long id;                    // 글 번호
    private String title;               // 글 제목
    private String writer;              // 작성자
    private String contents;            // 내용
    private boolean isPinned;           // 최상단 고정 여부
    private TargetRange targetRange;    // 공개 범위 : 전체/반(웹앱/스마트팩토리/SW임베디드/IT보안/클라우드)
    private LocalDate targetDate;       // 공개 날짜
    private LocalDateTime registedAt;   // (최초)작성일
    private Type type;                  // 글 유형 (공지/설문)


    public static NoticeDto toDto(Notice notice) {
        return NoticeDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .writer(notice.getWriter())
                .contents(notice.getContents())
                .isPinned(notice.isPinned())
                .targetRange(notice.getTargetRange())
                .targetDate(notice.getTargetDate())
                .type(notice.getType())
                .registedAt(notice.getRegistedAt())
                .build();
    }
}