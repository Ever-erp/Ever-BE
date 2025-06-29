package com.example.autoever_1st.notice.dto.req;

import com.example.autoever_1st.notice.constant.TargetRange;
import com.example.autoever_1st.notice.constant.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class NoticeWriteDto {          // 게시물 "작성" DTO (요청)
    @NotBlank(message = "제목은 공백일 수 없습니다.")
    private String title;              // 제목

    @NotBlank(message = "내용은 공백일 수 없습니다.")
    private String contents;           // 내용

    @NotNull(message = "상단 고정 여부 선택은 필수입니다.")
    private Boolean isPinned;          // 최상단 고정 여부

    @NotNull(message = "공개 범위 선택은 필수입니다")
    private TargetRange targetRange;   // 공개 범위(enum) : 전체/반(웹앱/스마트팩토리/SW임베디드/IT보안/클라우드)

    // @FutureOrPresent로 오늘 또는 미래날짜만 허용 가능 (날짜 정합성 검사 가능)
    // but, 지난 공지 수정 시 지난 날로 targetDate 설정할 때 문제 발생 -> 일단 NotNull 처리 ( createNotice만 해당되게 고려 )
    private LocalDate targetDate;      // 공개 기한

    @NotNull(message = "글 유형을 선택하세요.")
    private Type type;                 // 글 유형 (공지/설문)
}