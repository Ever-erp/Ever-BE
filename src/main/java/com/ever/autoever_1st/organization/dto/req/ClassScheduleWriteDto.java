package com.ever.autoever_1st.organization.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassScheduleWriteDto {
    @NotBlank(message = "수업명은 공백일 수 없습니다.")
    private String subjectName;
    @NotNull(message = "시작 날짜 선택은 필수입니다.")
    private LocalDate startDate;
    @NotNull(message = "종료 날짜 선택은 필수입니다.")
    private LocalDate endDate;
    @NotNull
    private String classDesc;
    @NotNull
    private String classUrl;
    @NotNull
    private Long classId;
}
