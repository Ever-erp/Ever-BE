package com.example.autoever_1st.class_schedule.service;

import com.example.autoever_1st.class_schedule.dto.ExtendedClassScheduleDto;

import java.util.List;

public interface ClassScheduleService {
    // 모든 수업 일정 조회
    List<ExtendedClassScheduleDto> findAll();

    // 반(ID)으로 수업 일정 조회
    List<ExtendedClassScheduleDto> findByClassId(Long classId);

    // 수업명으로 일정 조회
    List<ExtendedClassScheduleDto> findBySubjectName(String subjectName);

    // 수업 설명으로 일정 조회
    List<ExtendedClassScheduleDto> findByClassDesc(String classDesc);
}
