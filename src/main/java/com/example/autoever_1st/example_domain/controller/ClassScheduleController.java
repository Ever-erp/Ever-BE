package com.example.autoever_1st.example_domain.controller;


import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.example_domain.dto.res.ClassScheduleResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

//@Slf4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/class-schedule")
public class ClassScheduleController {
    private ClassScheduleResDto testClassSchedule() {
        // 초기값
        ClassScheduleResDto testDto = new ClassScheduleResDto();
        testDto.setClassName("Spring Boot");
        testDto.setStartDate(LocalDate.of(2020, 6, 18));
        testDto.setEndDate(LocalDate.of(2020, 6, 19));
        testDto.setClassDesc("JWT 서비스 구현하기");
        testDto.setClassUrl("https://www.notion.so/JWT-c7a1d10d70e34dcdb30e4118c4fe88d9?pvs=25");
        return testDto;
    }

    @GetMapping
    public ApiResponse<ClassScheduleResDto> mockClassScheduleGet() {
        return ApiResponse.success(testClassSchedule(),200);
    }

    @PostMapping
    public ApiResponse<ClassScheduleResDto> mockClassSchedulePost(/*@RequestBody ClassScheduleResDto classScheduleResDto*/) {
        ClassScheduleResDto test = testClassSchedule();
        test.setClassName("POST 확인");
        test.setClassDesc("POST 확인");
        test.setClassUrl("POST 확인");
        return ApiResponse.success(testClassSchedule(),201);
    }

    @PutMapping
    public ApiResponse<ClassScheduleResDto> mockClassSchedulePut(/*@RequestBody ClassScheduleResDto classScheduleResDto*/) {
        ClassScheduleResDto test = testClassSchedule();
        test.setClassName("UPDATE 확인");
        test.setClassDesc("UPDATE 확인");
        test.setClassUrl("UPDATE 확인");
        return ApiResponse.success(test, 200);
    }

    @DeleteMapping
    public ApiResponse<ClassScheduleResDto> mockClassScheduleDelete() {
        return ApiResponse.success(null, 200);
    }
}
