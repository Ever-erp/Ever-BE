package com.example.autoever_1st.monthly_schedule.service;

import com.example.autoever_1st.notice.dto.res.NoticeDto;
import com.example.autoever_1st.organization.dto.res.ClassScheduleResDto;
import com.example.autoever_1st.vacation.dto.VacationScheduleDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MonthlyScheduleService {

    List<NoticeDto> getNoticesByYearAndMonth(int year, int month, Authentication authentication);
    List<VacationScheduleDto> getVacationsByYearAndMonth(int year, int month, Authentication authentication);
    List<ClassScheduleResDto> getClassesByYearAndMonth(int year, int month, Authentication authentication);

}