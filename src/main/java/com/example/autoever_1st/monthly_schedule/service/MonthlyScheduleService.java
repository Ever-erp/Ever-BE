package com.example.autoever_1st.monthly_schedule.service;

import com.example.autoever_1st.monthly_schedule.dto.MonthlyScheduleDto;
import com.example.autoever_1st.notice.service.NoticeService;
import com.example.autoever_1st.organization.service.ClassScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonthlyScheduleService {

    private final NoticeService noticeService;
//    private final VacationService vacationService;
    private final ClassScheduleService classScheduleService;

    public MonthlyScheduleDto getMonthlySchedule(int year, int month) {
        return MonthlyScheduleDto.builder()
                .notices(noticeService.getNoticesByYearAndMonth(year, month))
//                .vacations(vacationService.getVacationsByYearAndMonth(year, month))
                .classes(classScheduleService.getNoticesByYearAndMonth(year, month))
                .build();
    }
}