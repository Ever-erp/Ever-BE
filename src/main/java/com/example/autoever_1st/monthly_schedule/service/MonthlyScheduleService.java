package com.example.autoever_1st.monthly_schedule.service;

import com.example.autoever_1st.monthly_schedule.dto.MonthlyScheduleDto;
import com.example.autoever_1st.notice.service.NoticeService;
import com.example.autoever_1st.organization.service.ClassScheduleService;
import com.example.autoever_1st.vacation.service.VacationScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonthlyScheduleService {

    private final NoticeService noticeService;
    private final VacationScheduleService vacationScheduleService;
    private final ClassScheduleService classScheduleService;

    public MonthlyScheduleDto getMonthlySchedule(int year, int month, Authentication authentication) {
        return MonthlyScheduleDto.builder()
                .notices(noticeService.getNoticesByYearAndMonth(year, month))
                .vacations(vacationScheduleService.getNoticesByYearAndMonth(year, month, authentication))
                .classes(classScheduleService.getNoticesByYearAndMonth(year, month, authentication))
                .build();
    }
}