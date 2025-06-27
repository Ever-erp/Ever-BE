package com.example.autoever_1st.notice.dto.res;

import com.example.autoever_1st.notice.constant.TargetRange;
import com.example.autoever_1st.notice.constant.Type;
import com.example.autoever_1st.notice.entities.Notice;
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

//    public static NoticeDto toDto(Notice n) {
//        return NoticeDto.builder()
//                .id(n.getId())
//                .title(n.getTitle())
//                .contents(n.getContents())
//                .writer(n.getWriter())
//                .isPinned(n.isPinned())
//                .targetRange(n.getTargetRange())
//                .targetDate(n.getTargetDate())
//                .type(n.getType())
//                .registedAt(n.getRegistedAt())
//                .build();
//    }

    public static NoticeDto toDto(Notice notice) {
        String writerName = null;
        if (notice.getMember() == null) {
            // 멤버 정보가 아예 없는 경우 (탈퇴 또는 삭제)
            writerName = "(삭제됨)";
        } else if (!notice.getMember().isActive()) {
            // 멤버는 있지만 비활성화 상태인 경우
            writerName = "(비활성화)";
        } else {
            // 정상 활성화 회원인 경우
            writerName = notice.getWriter();
        }
        return NoticeDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .writer(writerName)
                .contents(notice.getContents())
                .isPinned(notice.isPinned())
                .targetRange(notice.getTargetRange())
                .targetDate(notice.getTargetDate())
                .type(notice.getType())
                .registedAt(notice.getRegistedAt())
                .build();
    }
}