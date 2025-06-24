package com.example.autoever_1st.organization.dto.common;

import com.example.autoever_1st.organization.entities.ClassSchedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ClassWithScheduleDto {
    private Long classId;
    private String name;
    private int cohort;
    private List<ClassScheduleDto> schedules;
}
