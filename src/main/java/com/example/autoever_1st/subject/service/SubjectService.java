package com.example.autoever_1st.subject.service;

import com.example.autoever_1st.subject.dto.SubjectDto;

import java.util.List;

public interface SubjectService {
    // 모든 수업 일정 조회
    List<SubjectDto> findAll();

    // 반(ClassID)으로 수업 일정 조회
    List<SubjectDto> findByClassId(Long classId);

    // 수업명으로 일정 조회
    List<SubjectDto> findBySubjectName(String subjectName);

    // 수업 설명으로 일정 조회
    List<SubjectDto> findByClassDesc(String classDesc);
}
