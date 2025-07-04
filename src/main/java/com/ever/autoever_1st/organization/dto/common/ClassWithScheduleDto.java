package com.ever.autoever_1st.organization.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClassWithScheduleDto {
    private Long classId;
    private String name;
    private int cohort;
    private List<ClassScheduleDto> schedules;
}
