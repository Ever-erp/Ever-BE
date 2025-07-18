package com.ever.autoever_1st.monthly_schedule.controller;

import com.ever.autoever_1st.common.dto.response.ApiResponse;
import com.ever.autoever_1st.monthly_schedule.dto.MonthlyScheduleDto;
import com.ever.autoever_1st.monthly_schedule.service.MonthlyScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class MonthlyScheduleController {

    private final MonthlyScheduleService monthlyScheduleService;

    @GetMapping
    public ApiResponse<MonthlyScheduleDto> getSchedule(@RequestParam int year,
                                                       @RequestParam int month
    , Authentication authentication) {

        MonthlyScheduleDto monthlyScheduleDto = MonthlyScheduleDto.builder()
                .notices(monthlyScheduleService.getNoticesByYearAndMonth(year, month,authentication))
                .vacations(monthlyScheduleService.getVacationsByYearAndMonth(year, month, authentication))
                .classes(monthlyScheduleService.getClassesByYearAndMonth(year, month, authentication))
                .build();

        return ApiResponse.success(monthlyScheduleDto, HttpStatus.OK.value());
    }
}
