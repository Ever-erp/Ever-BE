package com.example.autoever_1st.organization.service;

import com.example.autoever_1st.organization.dto.common.ClassScheduleDto;

import java.util.List;

public interface ClassScheduleService {
    // 모든 수업 일정 조회
    List<ClassScheduleDto> findAll();

    // 반(ClassID)으로 수업 일정 조회
    List<ClassScheduleDto> findByClassId(Long classId);

    // 수업명으로 일정 조회
    List<ClassScheduleDto> findBySubjectName(String subjectName);

    // 수업 설명으로 일정 조회
    List<ClassScheduleDto> findByClassDesc(String classDesc);
}
