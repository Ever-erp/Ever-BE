package com.example.autoever_1st.subject.repository;

import com.example.autoever_1st.organization.entities.ClassSchedule;
import com.example.autoever_1st.organization.repository.ClassScheduleRepository;

import java.util.List;

public interface ExtendedClassScheduleRepository extends ClassScheduleRepository {

    // 반(ClassID)으로 수업 찾기
    List<ClassSchedule> findByClassEntityId(Long classId);

    // 수업명으로 검색
    List<ClassSchedule> findBySubjectNameContaining(String subjectName);

    // 수업 내용으로 검색
    List<ClassSchedule> findByClassDesc(String classDesc);

    /*  고급 기능
    // 날짜 범위로 조회 (시작일 기준)
    List<ClassSchedule> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    // 특정 클래스의 특정 날짜 범위 수업 조회
    List<ClassSchedule> findByClassEntityIdAndStartDateBetween(
            Long classId, LocalDate startDate, LocalDate endDate);

    // Join Fetch를 사용한 클래스 정보와 함께 조회
    @Query("SELECT cs FROM ClassSchedule cs JOIN FETCH cs.classEntity WHERE cs.id = :id")
    Optional<ClassSchedule> findByIdWithClass(@Param("id") Long id);

    // 특정 클래스의 수업들을 클래스 정보와 함께 조회
    @Query("SELECT cs FROM ClassSchedule cs JOIN FETCH cs.classEntity WHERE cs.classEntity.id = :classId ORDER BY cs.startDate")
    List<ClassSchedule> findByClassIdWithClass(@Param("classId") Long classId);

    // 진행 중인 수업 조회 (현재 날짜가 수업 기간에 포함)
    @Query("SELECT cs FROM ClassSchedule cs WHERE :currentDate BETWEEN cs.startDate AND cs.endDate")
    List<ClassSchedule> findCurrentSchedules(@Param("currentDate") LocalDate currentDate);

    // 특정 클래스의 진행 중인 수업 조회
    @Query("SELECT cs FROM ClassSchedule cs WHERE cs.classEntity.id = :classId AND :currentDate BETWEEN cs.startDate AND cs.endDate")
    List<ClassSchedule> findCurrentSchedulesByClassId(@Param("classId") Long classId, @Param("currentDate") LocalDate currentDate);

    // 종료된 수업 조회
    @Query("SELECT cs FROM ClassSchedule cs WHERE cs.endDate < :currentDate ORDER BY cs.endDate DESC")
    List<ClassSchedule> findCompletedSchedules(@Param("currentDate") LocalDate currentDate);

    // 예정된 수업 조회
    @Query("SELECT cs FROM ClassSchedule cs WHERE cs.startDate > :currentDate ORDER BY cs.startDate ASC")
    List<ClassSchedule> findUpcomingSchedules(@Param("currentDate") LocalDate currentDate);

    // 클래스별 수업 개수 조회
    @Query("SELECT COUNT(cs) FROM ClassSchedule cs WHERE cs.classEntity.id = :classId")
    Long countByClassId(@Param("classId") Long classId);
    */
}
