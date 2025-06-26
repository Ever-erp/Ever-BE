package com.example.autoever_1st.organization.service;

import com.example.autoever_1st.organization.dto.req.ClassScheduleWriteDto;
import com.example.autoever_1st.organization.dto.res.ClassScheduleResDto;

import java.util.List;

public interface ClassScheduleService {
    // 수업 생성
    ClassScheduleResDto createClassSchedule(ClassScheduleWriteDto classScheduleWriteDto);

    // 수업 수정
    ClassScheduleResDto updateClassSchedule(Long id, ClassScheduleWriteDto classScheduleWriteDto);

    // 수업 삭제
    void deleteClassSchedule(Long id);

    // 수업(id)으로 수업 일정 조회
    ClassScheduleResDto findById(Long id);

    // 모든 수업 일정 조회
    List<ClassScheduleResDto> findAll();

    // 수업명으로 일정 조회
    List<ClassScheduleResDto> findBySubjectName(String subjectName);

    // 수업 설명으로 일정 조회
    List<ClassScheduleResDto> findByClassDesc(String classDesc);

    // 연/월로 해당하는 공지 전체 조회 (캘린더용)
    List<ClassScheduleResDto> getNoticesByYearAndMonth(int year, int month);
}
