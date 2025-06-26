package com.example.autoever_1st.vacation.mapper;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.vacation.dto.VacationScheduleDto;
import com.example.autoever_1st.vacation.dto.VacationScheduleWriteDto;
import com.example.autoever_1st.vacation.entities.VacationSchedule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component  // 스프링이 자동으로 빈으로 등록
@RequiredArgsConstructor
@Slf4j
public class VacationScheduleMapper {

    private final MemberRepository memberRepository;

    public VacationScheduleDto toDto(VacationSchedule vacationSchedule, Authentication authentication) {
        // 로그인한 사용자 정보 가져오기
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + email));

        return VacationScheduleDto.builder()
                .id(vacationSchedule.getId())
                .vacationDate(vacationSchedule.getVacationDate())
                .vacationType(vacationSchedule.getVacationType())
                .vacationDesc(vacationSchedule.getVacationDesc())
                .memberId(vacationSchedule.getMember().getId())
                .build();
    }

    public VacationSchedule toEntity(VacationScheduleWriteDto vacationScheduleWriteDto, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberEmail));
        return VacationSchedule.builder()
                .vacationDate(vacationScheduleWriteDto.getVacationDate())
                .vacationType(vacationScheduleWriteDto.getVacationType())
                .vacationDesc(vacationScheduleWriteDto.getVacationDesc())
                .member(member)
                .build();

    }
}