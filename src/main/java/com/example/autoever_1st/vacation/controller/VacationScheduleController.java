package com.example.autoever_1st.vacation.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.vacation.dto.VacationScheduleDto;
import com.example.autoever_1st.vacation.dto.VacationScheduleWriteDto;
import com.example.autoever_1st.vacation.service.VacationScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vacation-schedules")
@RequiredArgsConstructor
@Slf4j
@Validated
public class VacationScheduleController {
    private final VacationScheduleService vacationScheduleService;
    // 새 휴가 작성
    @PostMapping
    public ApiResponse<VacationScheduleDto> createVacationSchedule(
            @RequestBody @Valid VacationScheduleWriteDto vacationScheduleWriteDto, Authentication authentication) {
//        VacationScheduleDto vacationScheduleDto = vacationScheduleService.createVacationSchedule(vacationScheduleWriteDto,authentication);
        log.info("새 휴가 작성");
//        log.info("member_id : {}", vacationScheduleDto.getMemberId());
        return ApiResponse.success(
                vacationScheduleService.createVacationSchedule(vacationScheduleWriteDto,authentication),
                HttpStatus.CREATED.value()
        );
    }
    // 전체 휴가 조회
    @GetMapping
    public ApiResponse<List<VacationScheduleDto>> getAllVacationSchedules(Authentication authentication) {

        log.info("전체 휴가 스케줄 조회 요청");
        return ApiResponse.success(
                vacationScheduleService.findAll(authentication),
                HttpStatus.OK.value()
        );
    }
    // ID로 휴가 조회
    @GetMapping("/{id}")
    public ApiResponse<VacationScheduleDto> getVacationScheduleById(@PathVariable Long id, Authentication authentication) {

        log.info("휴가 스케줄 조회 요청 - ID: {}", id);
        return ApiResponse.success(
                vacationScheduleService.findById(id, authentication),
                HttpStatus.OK.value()
        );
    }
    // 수업 수정(PUT)
    @PutMapping("/{id}")
    public ApiResponse<VacationScheduleDto> updateVacationSchedule(
            @PathVariable Long id,
            @RequestBody @Valid VacationScheduleWriteDto vacationScheduleWriteDto, Authentication authentication) {

        log.info("휴가 수정 - ID: {}", id);
        return ApiResponse.success(
                vacationScheduleService.updateVacationSchedule(id, vacationScheduleWriteDto, authentication),
                HttpStatus.OK.value()
        );
    }
    // 수업 삭제
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteVacationSchedule(@PathVariable Long id, Authentication authentication) {

        log.info("휴가 삭제 - ID: {}", id);
        vacationScheduleService.deleteVacationSchedule(id, authentication);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }
}
