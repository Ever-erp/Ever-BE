package com.ever.autoever_1st.organization.service.Impl;

import com.ever.autoever_1st.common.exception.CustomStatus;
import com.ever.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.ever.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.ever.autoever_1st.organization.entities.ClassEntity;
import com.ever.autoever_1st.organization.entities.ClassSchedule;
import com.ever.autoever_1st.organization.entities.Position;
import com.ever.autoever_1st.organization.repository.ClassEntityRepository;
import com.ever.autoever_1st.organization.repository.ClassScheduleRepository;
import com.ever.autoever_1st.organization.repository.PositionRepository;
import com.ever.autoever_1st.organization.service.OrganizationService;
import com.ever.autoever_1st.auth.dto.res.MemberResponseDto;
import com.ever.autoever_1st.auth.entities.Member;
import com.ever.autoever_1st.auth.repository.MemberRepository;
import com.ever.autoever_1st.organization.dto.common.ClassScheduleDto;
import com.ever.autoever_1st.organization.dto.common.ClassWithScheduleDto;
import com.ever.autoever_1st.organization.dto.res.ClassDetailDto;
import com.ever.autoever_1st.organization.dto.res.OrgInitResDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final ClassEntityRepository classEntityRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final PositionRepository positionRepository;
    private final MemberRepository memberRepository;

    public OrganizationServiceImpl(ClassEntityRepository classEntityRepository, ClassScheduleRepository classScheduleRepository,
                                   PositionRepository positionRepository, MemberRepository memberRepository) {
        this.classEntityRepository = classEntityRepository;
        this.classScheduleRepository = classScheduleRepository;
        this.positionRepository = positionRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public OrgInitResDto getOrganizationInitData() {
        // 반 전체 조회
        List<ClassEntity> classEntities = classEntityRepository.findAll();
        // 모든 스케줄 + 소속 반 join fetch
        List<ClassSchedule> allSchedules = classScheduleRepository.findAllWithClassEntity();
        // 반 id로 스케줄 그룹핑
        Map<Long, List<ClassScheduleDto>> classIdToSchedules = allSchedules.stream().collect(Collectors.groupingBy(
                        cs -> cs.getClassEntity().getId(),
                        Collectors.mapping(cs -> new ClassScheduleDto(
                                cs.getId(),
                                cs.getSubjectName(),
                                cs.getStartDate(),
                                cs.getEndDate(),
                                cs.getClassDesc(),
                                cs.getClassUrl()
                        ), Collectors.toList())
                ));
        // ClassWithScheduleDto로 변환
        List<ClassWithScheduleDto> classes = classEntities.stream()
                .map(classEntity -> new ClassWithScheduleDto(
                        classEntity.getId(),
                        classEntity.getName(),
                        classEntity.getCohort(),
                        classIdToSchedules.getOrDefault(classEntity.getId(), List.of())
                ))
                .toList();

        // 강사 포지션 가져오기
        Position instructorPosition = positionRepository.findByRole("ROLE_강사")
                .orElseThrow(() -> new RuntimeException("강사 포지션이 존재하지 않습니다."));
        // 해당 포지션을 가진 멤버들 가져오기
        List<Member> instructors = memberRepository.findByPosition(instructorPosition);

        for (Member member:instructors) {
            member.getPosition();
        }

        List<MemberResponseDto> instructorDtos = instructors.stream()
                .map(MemberResponseDto::of)
                .toList();

        return new OrgInitResDto(classes, instructorDtos);
    }

    @Transactional
    @Override
    public ClassDetailDto getClassDetail(Long classId) {
        // 반 정보
        ClassEntity classEntity = classEntityRepository.findById(classId)
                .orElseThrow(() -> new DataNotFoundException("반 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        // 해당 반의 스케줄들
        List<ClassSchedule> schedules = classScheduleRepository.findByClassEntityId(classId);
        List<ClassScheduleDto> scheduleDtos = schedules.stream()
                .map(s -> new ClassScheduleDto(s.getId(), s.getSubjectName(), s.getStartDate(), s.getEndDate(), s.getClassDesc(), s.getClassUrl()))
                .toList();
        // 해당 반의 멤버들
        List<Member> members = memberRepository.findByClassEntityWithPosition(classEntity);

        for (Member member:members) {
            member.getPosition();
        }

        List<MemberResponseDto> memberDtos = members.stream()
                .map(MemberResponseDto::of)
                .toList();

        return new ClassDetailDto(
                classEntity.getId(),
                classEntity.getName(),
                classEntity.getCohort(),
                scheduleDtos,
                memberDtos
        );
    }

    @Transactional
    @Override
    public List<MemberResponseDto> searchMembersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("검색할 이름을 입력해주세요.", CustomStatus.INVALID_INPUT);
        }
        List<Member> members = memberRepository.findByNameContaining(name);

        for (Member member:members) {
            member.getPosition();
        }

        return members.stream()
                .map(MemberResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<MemberResponseDto> searchMembersInClass(Long classId, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("검색할 이름을 입력해주세요.", CustomStatus.INVALID_INPUT);
        }
        List<Member> members = memberRepository.findByClassEntityIdAndNameContaining(classId, name);

        for (Member member:members) {
            member.getPosition();
        }

        return members.stream()
                .map(MemberResponseDto::of)
                .collect(Collectors.toList());
    }
}
