package com.example.autoever_1st.organization.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.organization.dto.req.ClassScheduleWriteDto;
import com.example.autoever_1st.organization.dto.res.ClassScheduleResDto;
import com.example.autoever_1st.organization.service.ClassScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/class-schedules")
@RequiredArgsConstructor
@Slf4j
public class ClassScheduleController {
    private final ClassScheduleService classScheduleService;
//    새 수업 생성
//    * GET /api/class-schedules
    @PostMapping
    public ApiResponse<ClassScheduleResDto> createClassSchedule(
            @RequestBody @Valid ClassScheduleWriteDto classScheduleWriteDto) {
        log.info("새 수업 작성");
        return ApiResponse.success(classScheduleService.createClassSchedule(classScheduleWriteDto), HttpStatus.CREATED.value());
    }
//    전체 수업 스케줄 조회
//    * GET /api/class-schedules
    @GetMapping
    public ApiResponse<List<ClassScheduleResDto>> getAllClassSchedules(@RequestParam int year, @RequestParam int month) {
        log.info("전체 수업 스케줄 조회 요청");
        List<ClassScheduleResDto> classSchedules = classScheduleService.findAll();
        return ApiResponse.success(classSchedules, HttpStatus.OK.value());
    }

//    ID로 수업 스케줄 조회
//    * GET /api/class-schedules/{id}
    @GetMapping("/{id}")
    public ApiResponse<ClassScheduleResDto> getClassScheduleById(@PathVariable Long id) {
        log.info("수업 스케줄 조회 요청 - ID: {}", id);
        ClassScheduleResDto classSchedules = classScheduleService.findById(id);
        return ApiResponse.success(classSchedules, HttpStatus.OK.value());
    }
//    수업명으로 검색
//    * GET /api/class-schedules/search?keyword=검색어
    @GetMapping("/search/subject_name")
    public ApiResponse<List<ClassScheduleResDto>> searchClassSchedulesBySubjectName(@RequestParam String keyword) {
        log.info("수업명 검색 요청 - 키워드: {}", keyword);
        List<ClassScheduleResDto> classSchedules = classScheduleService.findBySubjectName(keyword);
        return ApiResponse.success(classSchedules,HttpStatus.OK.value());
    }

    //    수업 내용으로 검색
//    * GET /api/class-schedules/search?keyword=검색어
    @GetMapping("/search/class_desc")
    public ApiResponse<List<ClassScheduleResDto>> searchClassSchedulesByClassDesc(@RequestParam String keyword) {
        log.info("수업내용 검색 요청 - 키워드: {}", keyword);
        List<ClassScheduleResDto> classSchedules = classScheduleService.findByClassDesc(keyword);
        return ApiResponse.success(classSchedules,HttpStatus.OK.value());
    }

    // 수업 수정(PUT)
    @PutMapping("/{id}")
    public ApiResponse<ClassScheduleResDto> update(@PathVariable Long id, @RequestBody ClassScheduleWriteDto classScheduleWriteDto) {
        log.info("수업 수정");
        return ApiResponse.success(classScheduleService.updateClassSchedule(id,classScheduleWriteDto), HttpStatus.OK.value());
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteClassSchedule(@PathVariable Long id) {
        log.info("수업 삭제");
        classScheduleService.deleteClassSchedule(id);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }
}