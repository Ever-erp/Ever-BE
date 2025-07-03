package com.ever.autoever_1st.vacation.mapper;

import com.ever.autoever_1st.auth.entities.Member;
import com.ever.autoever_1st.auth.repository.MemberRepository;
import com.ever.autoever_1st.vacation.dto.VacationScheduleDto;
import com.ever.autoever_1st.vacation.dto.VacationScheduleWriteDto;
import com.ever.autoever_1st.vacation.entities.VacationSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component  // 스프링이 자동으로 빈으로 등록
@RequiredArgsConstructor
@Slf4j
public class VacationScheduleMapper {

    private final MemberRepository memberRepository;

    public VacationScheduleDto toDto(VacationSchedule vacationSchedule, Member member) {


        return VacationScheduleDto.builder()
                .id(vacationSchedule.getId())
                .vacationDate(vacationSchedule.getVacationDate())
                .vacationType(vacationSchedule.getVacationType())
                .vacationDesc(vacationSchedule.getVacationDesc())
                .memberName(member.getName())
                .memberId(member.getId())
                .build();
    }

    public VacationSchedule toEntity(VacationScheduleWriteDto vacationScheduleWriteDto, Member member) {

        return VacationSchedule.builder()
                .vacationDate(vacationScheduleWriteDto.getVacationDate())
                .vacationType(vacationScheduleWriteDto.getVacationType())
                .vacationDesc(vacationScheduleWriteDto.getVacationDesc())
                .member(member)
                .build();

    }
}