package com.example.autoever_1st.vacation.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationScheduleWriteDto {
    private LocalDate vacationDate;
    private String vacationType;
    private String vacationDesc;
}
