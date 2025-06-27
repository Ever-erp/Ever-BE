package com.example.autoever_1st.monthly_schedule.controller;

import com.example.autoever_1st.monthly_schedule.dto.MonthlyScheduleDto;
import com.example.autoever_1st.monthly_schedule.service.MonthlyScheduleService;
import lombok.RequiredArgsConstructor;
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
    public MonthlyScheduleDto getSchedule(@RequestParam int year,
                                          @RequestParam int month
    , Authentication authentication) {
        return monthlyScheduleService.getMonthlySchedule(year, month,authentication);
    }
}
