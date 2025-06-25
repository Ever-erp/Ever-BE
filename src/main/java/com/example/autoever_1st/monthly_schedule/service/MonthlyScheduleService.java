package com.example.autoever_1st.monthly_schedule.service;

import com.example.autoever_1st.monthly_schedule.dto.MonthlyScheduleDto;
import com.example.autoever_1st.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonthlyScheduleService {

    private final NoticeService noticeService;
//    private final VacationService vacationService;
//    private final ClassService classService;

    public MonthlyScheduleDto getMonthlySchedule(int year, int month) {
        return MonthlyScheduleDto.builder()
                .notices(noticeService.getNoticesByYearAndMonth(year, month))
//                .vacations(vacationService.getVacationsByYearAndMonth(year, month))
//                .classes(classService.getClassesByYearAndMonth(year, month))
                .build();
    }
}