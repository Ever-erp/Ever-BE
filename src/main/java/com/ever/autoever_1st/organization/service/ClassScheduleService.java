package com.ever.autoever_1st.organization.service;

import com.ever.autoever_1st.organization.dto.req.ClassScheduleWriteDto;
import com.ever.autoever_1st.organization.dto.res.ClassScheduleResDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClassScheduleService {
    // 수업 생성
    ClassScheduleResDto createClassSchedule(ClassScheduleWriteDto classScheduleWriteDto, Authentication authentication);

    // 수업 수정
    ClassScheduleResDto updateClassSchedule(Long id, ClassScheduleWriteDto classScheduleWriteDto, Authentication authentication);

    // 수업 삭제
    void deleteClassSchedule(Long id, Authentication authentication);

    // 수업(id)으로 수업 일정 조회
    ClassScheduleResDto findById(Long id, Authentication authentication);

    // 수업명으로 일정 조회
    List<ClassScheduleResDto> findBySubjectName(String subjectName, Authentication authentication);

    // 수업 설명으로 일정 조회
    List<ClassScheduleResDto> findByClassDesc(String classDesc, Authentication authentication);
}
