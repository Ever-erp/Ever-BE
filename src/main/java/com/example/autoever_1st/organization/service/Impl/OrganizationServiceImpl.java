package com.example.autoever_1st.organization.service.Impl;

import com.example.autoever_1st.auth.dto.res.MemberResponseDto;
import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.example.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.example.autoever_1st.organization.dto.common.ClassScheduleDto;
import com.example.autoever_1st.organization.dto.common.ClassWithScheduleDto;
import com.example.autoever_1st.organization.dto.res.ClassDetailDto;
import com.example.autoever_1st.organization.dto.res.OrgInitResDto;
import com.example.autoever_1st.organization.entities.ClassEntity;
import com.example.autoever_1st.organization.entities.ClassSchedule;
import com.example.autoever_1st.organization.entities.Position;
import com.example.autoever_1st.organization.repository.ClassEntityRepository;
import com.example.autoever_1st.organization.repository.ClassScheduleRepository;
import com.example.autoever_1st.organization.repository.PositionRepository;
import com.example.autoever_1st.organization.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public OrgInitResDto getOrganizationInitData() {
        List<ClassWithScheduleDto> classes = classEntityRepository.findAll().stream()
                .map(classEntity -> {
                    List<ClassScheduleDto> schedules = classScheduleRepository.findByClassEntityId(classEntity.getId())
                            .stream()
                            .map(schedule -> new ClassScheduleDto(
                                    schedule.getId(),
                                    schedule.getSubjectName(),
                                    schedule.getStartDate(),
                                    schedule.getEndDate(),
                                    schedule.getClassDesc(),
                                    schedule.getClassUrl()
                            ))
                            .toList();

                    return new ClassWithScheduleDto(
                            classEntity.getId(),
                            classEntity.getName(),
                            classEntity.getCohort(),
                            schedules
                    );
                }).toList();

        // 강사 포지션 가져오기
        Position instructorPosition = positionRepository.findByRole("강사")
                .orElseThrow(() -> new RuntimeException("강사 포지션이 존재하지 않습니다."));
        // 해당 포지션을 가진 멤버들 가져오기
        List<Member> instructors = memberRepository.findByPosition(instructorPosition);
        List<MemberResponseDto> instructorDtos = instructors.stream()
                .map(MemberResponseDto::of)
                .toList();

        return new OrgInitResDto(classes, instructorDtos);
    }

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
        List<Member> members = memberRepository.findByClassEntity(classEntity);
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

    @Override
    public List<MemberResponseDto> searchMembersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("검색할 이름을 입력해주세요.", CustomStatus.INVALID_INPUT);
        }
        List<Member> members = memberRepository.findByNameContaining(name);
        return members.stream()
                .map(MemberResponseDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public List<MemberResponseDto> searchMembersInClass(Long classId, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("검색할 이름을 입력해주세요.", CustomStatus.INVALID_INPUT);
        }
        List<Member> members = memberRepository.findByClassEntityIdAndNameContaining(classId, name);
        return members.stream()
                .map(MemberResponseDto::of)
                .collect(Collectors.toList());
    }
}
