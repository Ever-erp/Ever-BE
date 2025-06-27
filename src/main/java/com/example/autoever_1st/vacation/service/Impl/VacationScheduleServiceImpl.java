package com.example.autoever_1st.vacation.service.Impl;


import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.vacation.dto.VacationScheduleDto;
import com.example.autoever_1st.vacation.dto.VacationScheduleWriteDto;
import com.example.autoever_1st.vacation.entities.VacationSchedule;
import com.example.autoever_1st.vacation.mapper.VacationScheduleMapper;
import com.example.autoever_1st.vacation.repository.VacationScheduleRepository;
import com.example.autoever_1st.vacation.service.VacationScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
        VacationSchedule entity = vacationScheduleMapper.toEntity(vacationScheduleWriteDto, authentication);
        VacationSchedule saved =  vacationScheduleRepository.save(entity);
        log.info("새 휴가 작성");
        VacationScheduleDto vacationScheduleDto = vacationScheduleMapper.toDto(saved,authentication);
        log.info("member_id : {}", vacationScheduleDto.getMemberName());
        try {
            log.info("RETURN DTO: {}", new ObjectMapper().writeValueAsString(vacationScheduleDto));
        } catch (JsonProcessingException e) {
            log.error("DTO 직렬화 실패", e);
        }
        return vacationScheduleDto;

    }

    // 휴가 조회 (단건)
    @Override
    @Transactional
    public VacationScheduleDto findById(Long id){
        VacationSchedule vacationSchedule = vacationScheduleRepository.findDtoById(id);
        log.info("휴가 ID로 단건 조회: {}", id);
        return VacationScheduleServiceImpl.toDto(vacationSchedule);
    }

    // 휴가 조회 (전체)
//    @Override
    @Transactional
    public List<VacationScheduleDto> findAll(Authentication authentication) {
        log.info("휴가 전체 조회");
        List<VacationSchedule> vacationSchedules = vacationScheduleRepository.findAll();
        List<VacationScheduleDto> dtos;
        return vacationScheduleRepository.findAll().stream()
                .map(vc -> vacationScheduleMapper.toDto(vc, authentication))
                .toList();
    }

    // 휴가 수정 (PUT)
    @Override @Transactional
    public VacationScheduleDto updateVacationSchedule(Long id, VacationScheduleWriteDto vacationScheduleWriteDto) {
        VacationSchedule vacationSchedule = vacationScheduleRepository.findDtoById(id);
        vacationSchedule.setVacationDate(vacationScheduleWriteDto.getVacationDate());
        vacationSchedule.setVacationType(vacationScheduleWriteDto.getVacationType());
        vacationSchedule.setVacationDesc(vacationScheduleWriteDto.getVacationDesc());
        return toDto(vacationScheduleRepository.save(vacationSchedule));
    }

    // 수업 삭제
    @Override @Transactional
    public void deleteVacationSchedule(Long id) {
        VacationSchedule vacationSchedule = vacationScheduleRepository.findDtoById(id);
        vacationScheduleRepository.delete(vacationSchedule);
    }

    // 연/월로 검색
    @Transactional
    @Override
    public List<VacationScheduleDto> getNoticesByYearAndMonth(int year, int month,Authentication authentication) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return vacationScheduleRepository.findByVacationDateIsNotNullAndVacationDateBetween(startDate, endDate).stream()
                .map(vc -> vacationScheduleMapper.toDto(vc, authentication))
                .toList();
    }

    public static VacationScheduleDto toDto(VacationSchedule vacationSchedule) {
        return VacationScheduleDto.builder()
                .id(vacationSchedule.getId())    // 필드명이 달라도 괜찮음
                .vacationDate(vacationSchedule.getVacationDate())
                .vacationType(vacationSchedule.getVacationType())
                .vacationDesc(vacationSchedule.getVacationDesc())
                .build();
    }

//    public static VacationSchedule toEntity(VacationScheduleWriteDto vacationScheduleWriteDto) {
//        return VacationSchedule.builder()
//                .vacationDate(vacationScheduleWriteDto.getVacationDate())
//                .vacationType(vacationScheduleWriteDto.getVacationType())
//                .vacationDesc(vacationScheduleWriteDto.getVacationDesc())
//                .build();
//    }
}
