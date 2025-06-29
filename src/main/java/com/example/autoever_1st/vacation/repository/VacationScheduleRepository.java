package com.example.autoever_1st.vacation.repository;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.organization.entities.ClassEntity;
import com.example.autoever_1st.vacation.entities.VacationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VacationScheduleRepository extends JpaRepository<VacationSchedule, Long> {
    // startDate와 endDate 사이에 있는 VacationSchedule(not null)
    List<VacationSchedule> findByVacationDateIsNotNullAndVacationDateBetween(
            LocalDate startDate, LocalDate endDate);
    List<VacationSchedule> findByMemberAndVacationDateIsNotNullAndVacationDateBetween(Member member, LocalDate start, LocalDate end);

    @Query("SELECT vs FROM VacationSchedule vs " + "WHERE vs.member.classEntity = :classEntity " +
            "AND vs.vacationDate IS NOT NULL " +
            "AND vs.vacationDate BETWEEN :startDate AND :endDate")
    List<VacationSchedule> findByClassEntityAndVacationDateBetween(@Param("classEntity") ClassEntity classEntity, @Param("startDate") LocalDate startDate,
                                                                   @Param("endDate") LocalDate endDate);

}
