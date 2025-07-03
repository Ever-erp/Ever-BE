package com.ever.autoever_1st.organization.controller;

import com.ever.autoever_1st.common.dto.response.ApiResponse;
import com.ever.autoever_1st.organization.dto.res.ClassScheduleResDto;
import com.ever.autoever_1st.organization.dto.req.ClassScheduleWriteDto;
import com.ever.autoever_1st.organization.service.ClassScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/class-schedules")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ClassScheduleController {
    private final ClassScheduleService classScheduleService;
//    새 수업 생성
    @PostMapping
    public ApiResponse<ClassScheduleResDto> createClassSchedule(
            @RequestBody @Valid ClassScheduleWriteDto classScheduleWriteDto, Authentication authentication) {
        log.info("새 수업 작성");
        return ApiResponse.success(classScheduleService.createClassSchedule(classScheduleWriteDto, authentication), HttpStatus.CREATED.value());
    }
//    전체 수업 스케줄 조회
    @GetMapping
    public ApiResponse<List<ClassScheduleResDto>> getAllClassSchedules(@RequestParam int year, @RequestParam int month, Authentication authentication) {
        log.info("전체 수업 스케줄 조회 요청");
        List<ClassScheduleResDto> classSchedules = classScheduleService.findAll(authentication);
        return ApiResponse.success(classSchedules, HttpStatus.OK.value());
    }

//    ID로 수업 스케줄 조회
    @GetMapping("/{id}")
    public ApiResponse<ClassScheduleResDto> getClassScheduleById(@PathVariable Long id, Authentication authentication) {
        log.info("수업 스케줄 조회 요청 - ID: {}", id);
        ClassScheduleResDto classSchedules = classScheduleService.findById(id, authentication);
        return ApiResponse.success(classSchedules, HttpStatus.OK.value());
    }
//    수업명으로 검색
    @GetMapping("/search/subject_name")
    public ApiResponse<List<ClassScheduleResDto>> searchClassSchedulesBySubjectName(@RequestParam String keyword, Authentication authentication) {
        log.info("수업명 검색 요청 - 키워드: {}", keyword);
        List<ClassScheduleResDto> classSchedules = classScheduleService.findBySubjectName(keyword, authentication);
        return ApiResponse.success(classSchedules,HttpStatus.OK.value());
    }

    //    수업 내용으로 검색
    @GetMapping("/search/class_desc")
    public ApiResponse<List<ClassScheduleResDto>> searchClassSchedulesByClassDesc(@RequestParam String keyword, Authentication authentication) {
        log.info("수업내용 검색 요청 - 키워드: {}", keyword);
        List<ClassScheduleResDto> classSchedules = classScheduleService.findByClassDesc(keyword, authentication);
        return ApiResponse.success(classSchedules,HttpStatus.OK.value());
    }

    // 수업 수정(PUT)
    @PutMapping("/{id}")
    public ApiResponse<ClassScheduleResDto> update(@PathVariable Long id, @RequestBody @Valid ClassScheduleWriteDto classScheduleWriteDto, Authentication authentication) {
        log.info("수업 수정");
        return ApiResponse.success(classScheduleService.updateClassSchedule(id,classScheduleWriteDto,authentication), HttpStatus.OK.value());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteClassSchedule(@PathVariable Long id, Authentication authentication) {
        log.info("수업 삭제");
        classScheduleService.deleteClassSchedule(id, authentication);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }
}