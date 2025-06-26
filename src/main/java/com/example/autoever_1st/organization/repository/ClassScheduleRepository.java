package com.example.autoever_1st.organization.repository;

import com.example.autoever_1st.organization.entities.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    // 반(ClassID)으로 수업 찾기
    List<ClassSchedule> findByClassEntityId(Long classId);

    // 수업명으로 검색
    List<ClassSchedule> findBySubjectNameContaining(String subjectName);

    // 수업 내용으로 검색
    List<ClassSchedule> findByClassDesc(String classDesc);
}
