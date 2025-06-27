package com.example.autoever_1st.organization.repository;

import com.example.autoever_1st.organization.entities.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("SELECT cs FROM ClassSchedule cs " +
            "WHERE cs.classEntity.id = :classId " + "AND cs.startDate <= :endDate " + "AND cs.endDate >= :startDate")
    List<ClassSchedule> findByClassAndExactMonth(@Param("classId") Long classId, @Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate);
}
