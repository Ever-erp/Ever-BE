package com.ever.autoever_1st.organization.dto.common;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassScheduleDto {
    private Long id;
    private String subjectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String classDesc;
    private String classUrl;
}
