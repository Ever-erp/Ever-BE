package com.example.autoever_1st.vacation.repository;

import com.example.autoever_1st.vacation.entities.VacationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VacationScheduleRepository extends JpaRepository<VacationSchedule, Long> {
    // 휴가(Id)로 수업 일정 조회
    VacationSchedule findDtoById(Long id);

    List<VacationSchedule> findByVacationDateIsNotNullAndVacationDateBetween(
            LocalDate startDate, LocalDate endDate);
}
