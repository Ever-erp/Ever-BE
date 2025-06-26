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
    public ApiResponse<List<ClassScheduleResDto>> getAllClassSchedules() {
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

//    /*
//    /**
//     * 날짜 범위로 수업 조회
//     * GET /api/class-schedules/date-range?startDate=2024-01-01&endDate=2024-12-31
//     */
//    @GetMapping("/date-range")
//    public ResponseEntity<List<ExtendedClassScheduleDto>> getClassSchedulesByDateRange(
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        log.info("날짜 범위 수업 조회 요청 - 시작일: {}, 종료일: {}", startDate, endDate);
//        List<ClassSchedule> classSchedules = classScheduleService.findByDateRange(startDate, endDate);
//        return ResponseEntity.ok(classSchedules);
//    }
//
//    /**
//     * 특정 클래스의 날짜 범위 수업 조회
//     * GET /api/class-schedules/class/{classId}/date-range?startDate=2024-01-01&endDate=2024-12-31
//     */
//    @GetMapping("/class/{classId}/date-range")
//    public ResponseEntity<List<ExtendedClassScheduleDto>> getClassSchedulesByClassIdAndDateRange(
//            @PathVariable Long classId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        log.info("클래스별 날짜 범위 수업 조회 요청 - 클래스 ID: {}, 시작일: {}, 종료일: {}", classId, startDate, endDate);
//        List<ClassSchedule> classSchedules = classScheduleService.findByClassIdAndDateRange(classId, startDate, endDate);
//        return ResponseEntity.ok(classSchedules);
//    }
//
//    /**
//     * 현재 진행 중인 수업 조회
//     * GET /api/class-schedules/current
//     */
//    @GetMapping("/current")
//    public ResponseEntity<List<ExtendedClassScheduleDto>> getCurrentClassSchedules() {
//        log.info("현재 진행 중인 수업 조회 요청");
//        List<ClassSchedule> classSchedules = classScheduleService.findCurrentSubjects();
//        return ResponseEntity.ok(classSchedules);
//    }
//
//    /**
//     * 수업 존재 여부 확인
//     * GET /api/class-schedules/{id}/exists
//     */
//    @GetMapping("/{id}/exists")
//    public ResponseEntity<Boolean> checkClassScheduleExists(@PathVariable Long id) {
//        log.info("수업 스케줄 존재 여부 확인 요청 - ID: {}", id);
//        boolean exists = classScheduleService.existsById(id);
//        return ResponseEntity.ok(exists);
//    }

//    /**
//     * 전체 수업 개수 조회
//     * GET /api/class-schedules/count
//     */
//    @GetMapping("/count")
//    public ResponseEntity<Long> getClassScheduleCount() {
//        log.info("전체 수업 스케줄 개수 조회 요청");
//        long count = classScheduleService.countAll();
//        return ResponseEntity.ok(count);
//    }
}