package com.ever.autoever_1st.vacation.service.Impl;


import com.ever.autoever_1st.auth.entities.Member;
import com.ever.autoever_1st.auth.repository.MemberRepository;
import com.ever.autoever_1st.common.exception.CustomStatus;
import com.ever.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.ever.autoever_1st.vacation.dto.VacationScheduleDto;
import com.ever.autoever_1st.vacation.dto.VacationScheduleWriteDto;
import com.ever.autoever_1st.vacation.entities.VacationSchedule;
import com.ever.autoever_1st.vacation.mapper.VacationScheduleMapper;
import com.ever.autoever_1st.vacation.repository.VacationScheduleRepository;
import com.ever.autoever_1st.vacation.service.VacationScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacationScheduleServiceImpl implements VacationScheduleService {

    private final VacationScheduleRepository vacationScheduleRepository;
    private final MemberRepository memberRepository;   // memberId → Member 엔티티 변환용
    private final VacationScheduleMapper vacationScheduleMapper;

    // 휴가 작성
    @Override
    @Transactional
    @PreAuthorize("hasRole('학생')")
    public VacationScheduleDto createVacationSchedule(VacationScheduleWriteDto vacationScheduleWriteDto, Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + email));
        VacationSchedule entity = vacationScheduleMapper.toEntity(vacationScheduleWriteDto, member);
        VacationSchedule saved =  vacationScheduleRepository.save(entity);
        VacationScheduleDto vacationScheduleDto = vacationScheduleMapper.toDto(saved,member);
        log.info("member_id : {}", vacationScheduleDto.getMemberName());
        return vacationScheduleDto;
    }

    // 휴가 조회 (단건)
    @Override
    @Transactional
    public VacationScheduleDto findById(Long id, Authentication authentication){
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + email));
        VacationSchedule vacationSchedule = vacationScheduleRepository.findById(id).get();
        return VacationScheduleServiceImpl.toDto(vacationSchedule);
    }

    // 휴가 수정 (PUT)
    @Override @Transactional
    @PreAuthorize("hasRole('학생')")
    public VacationScheduleDto updateVacationSchedule(Long id, VacationScheduleWriteDto vacationScheduleWriteDto, Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + email));
        VacationSchedule vacationSchedule = vacationScheduleRepository.findById(id).get();
        vacationSchedule.setVacationDate(vacationScheduleWriteDto.getVacationDate());
        vacationSchedule.setVacationType(vacationScheduleWriteDto.getVacationType());
        vacationSchedule.setVacationDesc(vacationScheduleWriteDto.getVacationDesc());
        return toDto(vacationScheduleRepository.save(vacationSchedule));
    }

    // 휴가 삭제
    @Override @Transactional
    @PreAuthorize("hasRole('학생')")
    public void deleteVacationSchedule(Long id, Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + email));
        VacationSchedule vacationSchedule = vacationScheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 휴가 일정이 존재하지 않습니다."));
        // 본인 것인지 확인
        if (!vacationSchedule.getMember().getId().equals(member.getId())) {
            throw new ValidationException("본인의 휴가 일정이 아닙니다.", CustomStatus.INVALID_INPUT);
        }
        vacationScheduleRepository.delete(vacationSchedule);
    }

    public static VacationScheduleDto toDto(VacationSchedule vacationSchedule) {
        return VacationScheduleDto.builder()
                .id(vacationSchedule.getId())    // 필드명이 달라도 괜찮음
                .vacationDate(vacationSchedule.getVacationDate())
                .vacationType(vacationSchedule.getVacationType())
                .vacationDesc(vacationSchedule.getVacationDesc())
                .build();
    }
}
