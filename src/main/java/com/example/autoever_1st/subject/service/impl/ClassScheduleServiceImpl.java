package com.example.autoever_1st.subject.service.impl;

import com.example.autoever_1st.subject.dto.ExtendedClassScheduleDto;
import com.example.autoever_1st.subject.repository.ExtendedClassScheduleRepository;
import com.example.autoever_1st.subject.service.ClassScheduleService;
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

    private final ExtendedClassScheduleRepository extendedClassScheduleRepository;

    @Transactional  @Override
    public List<ExtendedClassScheduleDto> findAll() {
        log.info("전체 수업 일정 조회");
        return extendedClassScheduleRepository.findAll()
                .stream()
                .map(ClassScheduleServiceImpl::fromEntity)
                .collect(Collectors.toList());
    }
    // 반(ID)으로 수업 일정 조회
    @Transactional @Override
    public List<ExtendedClassScheduleDto> findByClassId(Long classId) {
        log.info("반 ID로 수업 일정 조회: {}", classId);
        return extendedClassScheduleRepository.findByClassEntityId(classId)
                .stream()
                .map(ClassScheduleServiceImpl::fromEntity)
                .collect(Collectors.toList());
    }

    // 수업명으로 일정 조회
    @Transactional
    @Override
    public List<ExtendedClassScheduleDto> findBySubjectName(String subjectName) {
        log.info("수업명으로 일정 조회: {}", subjectName);
        return extendedClassScheduleRepository.findBySubjectNameContaining(subjectName)
                .stream()
                .map(ClassScheduleServiceImpl::fromEntity)
                .collect(Collectors.toList());
    }

    // 수업 설명으로 일정 조회
    @Transactional
    @Override
    public List<ExtendedClassScheduleDto> findByClassDesc(String classDesc) {
        log.info("수업 설명으로 일정 조회: {}", classDesc);
        return extendedClassScheduleRepository.findByClassDesc(classDesc)
                .stream()
                .map(ClassScheduleServiceImpl::fromEntity)
                .collect(Collectors.toList());
    }
    public static ExtendedClassScheduleDto fromEntity(ClassSchedule classSchedule) {
        ClassScheduleDto classScheduleDto = new ClassScheduleDto(
                classSchedule.getId(),
                classSchedule.getSubjectName(),
                classSchedule.getStartDate(),
                classSchedule.getEndDate(),
                classSchedule.getClassDesc(),
                classSchedule.getClassUrl()
        );
        // builder가 없는 기존 dto
        // 그걸 호출하는 확장 dto의 extended
        // (기존 dto + 확장 dto)로 감싸서 한꺼번에 빌더 세팅하는 느낌
        return ExtendedClassScheduleDto.builder()
                .extended(classScheduleDto)
                // 확장 dto에 있는 필드를 추가로 매핑하면 됨
                // 예시) .extendedField()
                .build();
    }
}
