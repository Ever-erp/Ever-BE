package com.example.autoever_1st.organization.controller;

import com.example.autoever_1st.organization.dto.common.ClassScheduleDto;
import com.example.autoever_1st.organization.service.ClassScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/class-schedules")
@RequiredArgsConstructor
@Slf4j
public class ClassScheduleController {

    private final ClassScheduleService classScheduleService;


//    전체 수업 스케줄 조회
//    * GET /api/class-schedules

    @GetMapping
    public ResponseEntity<List<ClassScheduleDto>> getAllClassSchedules() {
        log.info("전체 수업 스케줄 조회 요청");
        List<ClassScheduleDto> classSchedules = classScheduleService.findAll();
        return ResponseEntity.ok(classSchedules);
    }

//    ID로 수업 스케줄 조회
//    * GET /api/class-schedules/{id}
    @GetMapping("/{id}")
    public ResponseEntity<List<ClassScheduleDto>> getClassScheduleById(@PathVariable Long classId) {
        log.info("수업 스케줄 조회 요청 - ID: {}", classId);
        List<ClassScheduleDto> classSchedules = classScheduleService.findByClassId(classId);
        return ResponseEntity.ok(classSchedules);
    }

//    클래스별 수업 스케줄 조회
//    * GET /api/class-schedules/class/{classId}
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<ClassScheduleDto>> findBySubjectName(@PathVariable String subjectName) {
        log.info("클래스별 수업 스케줄 조회 요청 - 클래스명: {}", subjectName);
        List<ClassScheduleDto> classSchedules = classScheduleService.findBySubjectName(subjectName);
        return ResponseEntity.ok(classSchedules);
    }

//    수업명으로 검색
//    * GET /api/class-schedules/search?keyword=검색어
    @GetMapping("/search")
    public ResponseEntity<List<ClassScheduleDto>> searchClassSchedules(@RequestParam String keyword) {
        log.info("수업명 검색 요청 - 키워드: {}", keyword);
        List<ClassScheduleDto> classSchedules = classScheduleService.findByClassDesc(keyword);
        return ResponseEntity.ok(classSchedules);
    }
///*
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