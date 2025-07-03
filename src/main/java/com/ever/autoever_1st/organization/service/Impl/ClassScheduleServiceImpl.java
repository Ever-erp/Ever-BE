package com.ever.autoever_1st.organization.service.Impl;

import com.ever.autoever_1st.auth.entities.Member;
import com.ever.autoever_1st.auth.repository.MemberRepository;
import com.ever.autoever_1st.common.exception.CustomStatus;
import com.ever.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.ever.autoever_1st.organization.dto.req.ClassScheduleWriteDto;
import com.ever.autoever_1st.organization.dto.res.ClassScheduleResDto;
import com.ever.autoever_1st.organization.entities.ClassEntity;
import com.ever.autoever_1st.organization.entities.ClassSchedule;
import com.ever.autoever_1st.organization.repository.ClassEntityRepository;
import com.ever.autoever_1st.organization.repository.ClassScheduleRepository;
import com.ever.autoever_1st.organization.service.ClassScheduleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassScheduleServiceImpl implements ClassScheduleService {
    private final MemberRepository memberRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final ClassEntityRepository classEntityRepository;

    // 수업 생성
    @Transactional @Override
    @PreAuthorize("hasRole('관리자')")
    public ClassScheduleResDto createClassSchedule(ClassScheduleWriteDto classScheduleWriteDto, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
        // 수업 날짜 중복 방지
        Long overlapCount = classScheduleRepository.countOverlappingSchedules(classScheduleWriteDto.getClassId(),
                classScheduleWriteDto.getStartDate(), classScheduleWriteDto.getEndDate());
        if (overlapCount> 0) {
            throw new ValidationException("수업일정이 중복되었습니다.", CustomStatus.INVALID_INPUT);
        }
        ClassEntity classEntity = classEntityRepository.findById(classScheduleWriteDto.getClassId()).get();
        ClassSchedule classSchedule = classScheduleRepository.save(toEntity(classScheduleWriteDto, classEntity));
        log.info("새 수업 작성");
        return toDto(classSchedule); // 생성된 객체만 반환
    }

    // 수업(ID)으로 수업 일정 조회
    @Transactional @Override
    public ClassScheduleResDto findById(Long id, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
        ClassSchedule classSchedule = classScheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("수업을 찾을 수 없습니다. : " + id));

        log.info("수업 ID로 수업 일정 조회: {}",id);
        return ClassScheduleServiceImpl.toDto(classSchedule);
    }


    // 수업명으로 일정 조회
    @Transactional
    @Override
    public List<ClassScheduleResDto> findBySubjectName(String subjectName, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
        log.info("수업명으로 일정 조회: {}", subjectName);
        return classScheduleRepository.findBySubjectNameContaining(subjectName)
                .stream()
                .map(ClassScheduleServiceImpl::toDto)
                .collect(Collectors.toList());
    }

    // 수업 설명으로 일정 조회
    @Transactional
    @Override
    public List<ClassScheduleResDto> findByClassDesc(String classDesc, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
        log.info("수업 설명으로 일정 조회: {}", classDesc);
        return classScheduleRepository.findByClassDescContaining(classDesc)
                .stream()
                .map(ClassScheduleServiceImpl::toDto)
                .collect(Collectors.toList());
    }

    // 수업 전체 수정
    @Override @Transactional
    @PreAuthorize("hasRole('관리자')")
    public ClassScheduleResDto updateClassSchedule(Long id, ClassScheduleWriteDto classScheduleWriteDto, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
        ClassSchedule classSchedule = classScheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("수업을 찾을 수 없습니다. : " + id));

        classSchedule.setSubjectName(classScheduleWriteDto.getSubjectName());
        classSchedule.setStartDate(classScheduleWriteDto.getStartDate());
        classSchedule.setEndDate(classScheduleWriteDto.getEndDate());
        classSchedule.setClassDesc(classScheduleWriteDto.getClassDesc());
        classSchedule.setClassUrl(classScheduleWriteDto.getClassUrl());

        return toDto(classScheduleRepository.save(classSchedule));
    }

    // 수업 삭제
    @Override @Transactional
    @PreAuthorize("hasRole('관리자')")
    public void deleteClassSchedule(Long id, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. : " + memberEmail));
        ClassSchedule classSchedule = classScheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("수업을 찾을 수 없습니다. : " + id));

        classScheduleRepository.delete(classSchedule);
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

    public static ClassSchedule toEntity(ClassScheduleWriteDto classScheduleWriteDto, ClassEntity classEntity) {
        return ClassSchedule.builder()
                .subjectName(classScheduleWriteDto.getSubjectName())
                .startDate(classScheduleWriteDto.getStartDate())
                .endDate(classScheduleWriteDto.getEndDate())
                .classDesc(classScheduleWriteDto.getClassDesc())
                .classUrl(classScheduleWriteDto.getClassUrl())
                .classEntity(classEntity)
                .build();
    }
}
