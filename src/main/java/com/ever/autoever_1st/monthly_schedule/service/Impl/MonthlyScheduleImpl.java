package com.ever.autoever_1st.monthly_schedule.service.Impl;

import com.ever.autoever_1st.auth.entities.Member;
import com.ever.autoever_1st.auth.repository.MemberRepository;
import com.ever.autoever_1st.monthly_schedule.service.MonthlyScheduleService;
import com.ever.autoever_1st.notice.dto.res.NoticeDto;
import com.ever.autoever_1st.notice.entities.Notice;
import com.ever.autoever_1st.notice.repository.NoticeRepository;
import com.ever.autoever_1st.organization.dto.res.ClassScheduleResDto;
import com.ever.autoever_1st.organization.entities.ClassEntity;
import com.ever.autoever_1st.organization.entities.ClassSchedule;
import com.ever.autoever_1st.organization.repository.ClassScheduleRepository;
import com.ever.autoever_1st.vacation.dto.VacationScheduleDto;
import com.ever.autoever_1st.vacation.entities.VacationSchedule;
import com.ever.autoever_1st.vacation.mapper.VacationScheduleMapper;
import com.ever.autoever_1st.vacation.repository.VacationScheduleRepository;
import com.ever.autoever_1st.organization.service.Impl.ClassScheduleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonthlyScheduleImpl implements MonthlyScheduleService {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final VacationScheduleRepository vacationScheduleRepository;
    private final VacationScheduleMapper vacationScheduleMapper;
    private final ClassScheduleRepository classScheduleRepository;


    @Override @Transactional(readOnly = true)
    public List<NoticeDto> getNoticesByYearAndMonth(int year, int month, Authentication authentication) {
        String memberEmail = authentication.getName();
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<Notice> notices = noticeRepository.findByTargetDateIsNotNullAndTargetDateBetweenOrderByIsPinnedDescRegistedAtDesc(startDate, endDate);
        return notices.stream()
                .map(NoticeDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<VacationScheduleDto> getVacationsByYearAndMonth(int year, int month, Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));
        ClassEntity classEntity = member.getClassEntity();
        if (classEntity == null) {
            throw new IllegalArgumentException("반 정보가 없습니다.");
        }
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<VacationSchedule> vacationSchedules = vacationScheduleRepository
                .findByClassEntityAndVacationDateBetween(classEntity, startDate, endDate);
        return vacationSchedules.stream()
                .map(vc -> vacationScheduleMapper.toDto(vc, vc.getMember()))
                .toList();
    }

    @Transactional
    @Override
    public List<ClassScheduleResDto> getClassesByYearAndMonth(int year, int month, Authentication authentication) {
        // 로그인한 유저 이메일
        String email = authentication.getName();
        // 유저 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        ClassEntity classEntity = member.getClassEntity();
        List<ClassSchedule> classSchedules = classScheduleRepository.findByClassAndExactMonth(classEntity.getId(), startDate, endDate);
        return classSchedules.stream()
                .map(ClassScheduleServiceImpl::toDto)
                .collect(Collectors.toList());
    }
}
