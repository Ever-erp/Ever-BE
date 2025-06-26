package com.example.autoever_1st.vacation.service;

import com.example.autoever_1st.vacation.dto.VacationScheduleDto;
import com.example.autoever_1st.vacation.dto.VacationScheduleWriteDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacationScheduleService {
        VacationScheduleDto createVacationSchedule(VacationScheduleWriteDto vacationScheduleWriteDto, Authentication authentication);

        VacationScheduleDto findById(Long id);

        List<VacationScheduleDto> findAll();

        VacationScheduleDto updateVacationSchedule(Long id, VacationScheduleWriteDto vacationScheduleWriteDto);

        void deleteVacationSchedule(Long id);

        // 연/월로 해당하는 공지 전체 조회 (캘린더용)
        List<VacationScheduleDto> getNoticesByYearAndMonth(int year, int month);
    }

