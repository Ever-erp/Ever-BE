package com.example.autoever_1st.vacation.service.Impl;


import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.vacation.dto.VacationScheduleDto;
import com.example.autoever_1st.vacation.dto.VacationScheduleWriteDto;
import com.example.autoever_1st.vacation.entities.VacationSchedule;
import com.example.autoever_1st.vacation.mapper.VacationScheduleMapper;
import com.example.autoever_1st.vacation.repository.VacationScheduleRepository;
import com.example.autoever_1st.vacation.service.VacationScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public VacationScheduleDto createVacationSchedule(VacationScheduleWriteDto vacationScheduleWriteDto,Authentication authentication) {
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

    // 휴가 조회 (전체)
    @Override
    @Transactional
    public List<VacationScheduleDto> findAll(Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + email));
        List<VacationSchedule> vacationSchedules = vacationScheduleRepository.findAll();
        List<VacationScheduleDto> dtos;
        return vacationScheduleRepository.findAll().stream()
                .map(vc -> vacationScheduleMapper.toDto(vc, member))
                .toList();
    }

    // 휴가 수정 (PUT)
    @Override @Transactional
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

    // 수업 삭제
    @Override @Transactional
    public void deleteVacationSchedule(Long id, Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + email));
        VacationSchedule vacationSchedule = vacationScheduleRepository.findById(id).get();
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
