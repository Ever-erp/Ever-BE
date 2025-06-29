package com.example.autoever_1st.vacation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationScheduleWriteDto {
    @NotNull(message = "휴가 날짜 선택은 필수입니다.")
    private LocalDate vacationDate;
    @NotNull(message = "휴가 유형 선택은 필수입니다.")
    private String vacationType;
    @NotNull(message = "휴가 사유 내용은 필수입니다.")
    private String vacationDesc;
}
