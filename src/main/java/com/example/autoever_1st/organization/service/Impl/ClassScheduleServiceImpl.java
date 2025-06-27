package com.example.autoever_1st.organization.service.Impl;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.organization.dto.req.ClassScheduleWriteDto;
import com.example.autoever_1st.organization.dto.res.ClassScheduleResDto;
import com.example.autoever_1st.organization.entities.ClassEntity;
import com.example.autoever_1st.organization.entities.ClassSchedule;
import com.example.autoever_1st.organization.repository.ClassScheduleRepository;
import com.example.autoever_1st.organization.service.ClassScheduleService;
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
public class ClassScheduleServiceImpl implements ClassScheduleService {
    private final MemberRepository memberRepository;
    private final ClassScheduleRepository classScheduleRepository;

    // 수업 생성
    @Transactional @Override
    public ClassScheduleResDto createClassSchedule(ClassScheduleWriteDto classScheduleWriteDto) {
        ClassSchedule classSchedule = classScheduleRepository.save(toEntity(classScheduleWriteDto));
        log.info("새 수업 작성");
        return toDto(classSchedule); // 생성된 객체만 반환
    }

    // 수업(ID)으로 수업 일정 조회
    @Transactional @Override
    public ClassScheduleResDto findById(Long id) {
        ClassSchedule classSchedule = classScheduleRepository.findDtoById(id);
        log.info("수업 ID로 수업 일정 조회: {}",id);
        return ClassScheduleServiceImpl.toDto(classSchedule);
    }

    // 전체 수업 일정 조회
    @Transactional  @Override
    public List<ClassScheduleResDto> findAll() {
        log.info("전체 수업 일정 조회");
        return classScheduleRepository.findAll()
                .stream()
                .map(ClassScheduleServiceImpl::toDto)
                .collect(Collectors.toList());
    }

    // 수업명으로 일정 조회
    @Transactional
    @Override
    public List<ClassScheduleResDto> findBySubjectName(String subjectName) {
        log.info("수업명으로 일정 조회: {}", subjectName);
        return classScheduleRepository.findBySubjectNameContaining(subjectName)
                .stream()
                .map(ClassScheduleServiceImpl::toDto)
                .collect(Collectors.toList());
    }

    // 수업 설명으로 일정 조회
    @Transactional
    @Override
    public List<ClassScheduleResDto> findByClassDesc(String classDesc) {
        log.info("수업 설명으로 일정 조회: {}", classDesc);
        return classScheduleRepository.findByClassDescContaining(classDesc)
                .stream()
                .map(ClassScheduleServiceImpl::toDto)
                .collect(Collectors.toList());
    }

    // 공지 전체 수정
    @Override @Transactional
    public ClassScheduleResDto updateClassSchedule(Long id, ClassScheduleWriteDto classScheduleWriteDto) {
        ClassSchedule classSchedule = classScheduleRepository.findDtoById(id);

        classSchedule.setSubjectName(classScheduleWriteDto.getSubjectName());
        classSchedule.setStartDate(classScheduleWriteDto.getStartDate());
        classSchedule.setEndDate(classScheduleWriteDto.getEndDate());
        classSchedule.setClassDesc(classScheduleWriteDto.getClassDesc());
        classSchedule.setClassUrl(classScheduleWriteDto.getClassUrl());

        return toDto(classScheduleRepository.save(classSchedule));
    }

    // 수업 삭제
    @Override @Transactional
    public void deleteClassSchedule(Long id) {
        ClassSchedule classSchedule = classScheduleRepository.findDtoById(id);
        classScheduleRepository.delete(classSchedule);
    }

    @Transactional
    @Override
    public List<ClassScheduleResDto> getNoticesByYearAndMonth(int year, int month, Authentication authentication) {
        // 로그인한 유저 이메일
        String email = authentication.getName();
        // 유저 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        ClassEntity classEntity = member.getClassEntity();
        List<ClassSchedule> classSchedules = classScheduleRepository.findByClassEntityAndEndDateGreaterThanEqualAndStartDateLessThanEqual(classEntity, startDate, endDate);
        return classSchedules.stream()
                .map(ClassScheduleServiceImpl::toDto)
                .collect(Collectors.toList());
    }


    public static ClassScheduleResDto toDto(ClassSchedule classSchedule) {
        return ClassScheduleResDto.builder()
                .id(classSchedule.getId())
                .subjectName(classSchedule.getSubjectName())
                .startDate(classSchedule.getStartDate())
                .endDate(classSchedule.getEndDate())
                .classDesc(classSchedule.getClassDesc())
                .classUrl(classSchedule.getClassUrl())
                .build();
    }

    public static ClassSchedule toEntity(ClassScheduleWriteDto classScheduleWriteDto) {
        return ClassSchedule.builder()
                .subjectName(classScheduleWriteDto.getSubjectName())
                .startDate(classScheduleWriteDto.getStartDate())
                .endDate(classScheduleWriteDto.getEndDate())
                .classDesc(classScheduleWriteDto.getClassDesc())
                .classUrl(classScheduleWriteDto.getClassUrl())
                .build();
    }
}
