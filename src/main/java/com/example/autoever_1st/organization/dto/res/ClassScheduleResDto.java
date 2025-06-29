package com.example.autoever_1st.organization.dto.res;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassScheduleResDto {
    private Long id;
    private String subjectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String classDesc;
    private String classUrl;
}
