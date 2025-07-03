package com.ever.autoever_1st.vacation.service;

import com.ever.autoever_1st.vacation.dto.VacationScheduleDto;
import com.ever.autoever_1st.vacation.dto.VacationScheduleWriteDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface VacationScheduleService {
        VacationScheduleDto createVacationSchedule(VacationScheduleWriteDto vacationScheduleWriteDto, Authentication authentication);

        VacationScheduleDto findById(Long id, Authentication authentication);

        List<VacationScheduleDto> findAll(Authentication authentication);

        VacationScheduleDto updateVacationSchedule(Long id, VacationScheduleWriteDto vacationScheduleWriteDtom, Authentication authentication);

        void deleteVacationSchedule(Long id, Authentication authentication);
    }

