package com.example.autoever_1st.organization.service.Impl;

import com.example.autoever_1st.organization.repository.ClassScheduleRepository;
import com.example.autoever_1st.organization.service.ClassScheduleService;
import com.example.autoever_1st.organization.dto.common.ClassScheduleDto;
import com.example.autoever_1st.organization.entities.ClassSchedule;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassScheduleServiceImpl implements ClassScheduleService {

    private final ClassScheduleRepository classScheduleRepository;

    @Transactional  @Override
    public List<ClassScheduleDto> findAll() {
        log.info("전체 수업 일정 조회");
        return classScheduleRepository.findAll()
                .stream()
                .map(ClassScheduleServiceImpl::fromEntity)
                .collect(Collectors.toList());
    }
    // 반(ID)으로 수업 일정 조회
    @Transactional @Override
    public List<ClassScheduleDto> findByClassId(Long classId) {
        log.info("반 ID로 수업 일정 조회: {}", classId);
        return classScheduleRepository.findByClassEntityId(classId)
                .stream()
                .map(ClassScheduleServiceImpl::fromEntity)
                .collect(Collectors.toList());
    }

    // 수업명으로 일정 조회
    @Transactional
    @Override
    public List<ClassScheduleDto> findBySubjectName(String subjectName) {
        log.info("수업명으로 일정 조회: {}", subjectName);
        return classScheduleRepository.findBySubjectNameContaining(subjectName)
                .stream()
                .map(ClassScheduleServiceImpl::fromEntity)
                .collect(Collectors.toList());
    }

    // 수업 설명으로 일정 조회
    @Transactional
    @Override
    public List<ClassScheduleDto> findByClassDesc(String classDesc) {
        log.info("수업 설명으로 일정 조회: {}", classDesc);
        return classScheduleRepository.findByClassDesc(classDesc)
                .stream()
                .map(ClassScheduleServiceImpl::fromEntity)
                .collect(Collectors.toList());
    }
    public static ClassScheduleDto fromEntity(ClassSchedule classSchedule) {
        return ClassScheduleDto.builder()
                .id(classSchedule.getId())
                .subjectName(classSchedule.getSubjectName())
                .startDate(classSchedule.getStartDate())
                .endDate(classSchedule.getEndDate())
                .classDesc(classSchedule.getClassDesc())
                .classUrl(classSchedule.getClassUrl())
                .build();
    }
}
