package com.ever.autoever_1st.vacation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacationScheduleDto {
    private Long id;                  // 엔티티 PK
    private LocalDate vacationDate;
    private String vacationType;
    private String vacationDesc;

    @JsonProperty("memberName")
    private String memberName;

    private Long memberId;
}
