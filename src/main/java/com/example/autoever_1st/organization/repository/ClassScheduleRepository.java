package com.example.autoever_1st.organization.repository;

import com.example.autoever_1st.organization.entities.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    // 수업(Id)으로 수업 일정 조회
    ClassSchedule findDtoById(Long id);

    // 반(ClassId)으로 수업 찾기
    List<ClassSchedule> findByClassEntityId(Long classId);

    // 수업명으로 검색
    List<ClassSchedule> findBySubjectNameContaining(String subjectName);

    // 수업 내용으로 검색
    List<ClassSchedule> findByClassDescContaining(String classDesc);

//  * (ClassSchedule.endDate >= :startDate)  AND  (ClassSchedule.startDate <= :endDate)
//  * ClassSchedule와 특정 연월(1일~말일), 두 구간이 하나라도 겹치면 매칭
    List<ClassSchedule> findByEndDateGreaterThanEqualAndStartDateLessThanEqual(
            LocalDate startDate,
            LocalDate endDate
    );
}
