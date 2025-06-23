package com.example.autoever_1st.organization.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ClassScheduleDto {
    private Long id;
    private String subjectName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String classDesc;
    private String classUrl;
}
