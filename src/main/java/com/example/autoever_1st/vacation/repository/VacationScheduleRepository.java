package com.example.autoever_1st.vacation.repository;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.vacation.entities.VacationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface VacationScheduleRepository extends JpaRepository<VacationSchedule, Long> {
    // startDate와 endDate 사이에 있는 VacationSchedule(not null)
    List<VacationSchedule> findByVacationDateIsNotNullAndVacationDateBetween(
            LocalDate startDate, LocalDate endDate);
    List<VacationSchedule> findByMemberAndVacationDateIsNotNullAndVacationDateBetween(Member member, LocalDate start, LocalDate end);

}
