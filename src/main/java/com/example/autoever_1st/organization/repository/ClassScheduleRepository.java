package com.example.autoever_1st.organization.repository;

import com.example.autoever_1st.organization.entities.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findByClassEntityId(Long classId);
    @Query("SELECT cs FROM ClassSchedule cs JOIN FETCH cs.classEntity")
    List<ClassSchedule> findAllWithClassEntity();
}
