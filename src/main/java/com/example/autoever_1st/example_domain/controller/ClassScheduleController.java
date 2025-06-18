package com.example.autoever_1st.example_domain.controller;


import com.example.autoever_1st.example_domain.dto.res.ClassScheduleResDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

//@Slf4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
//@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/class-schedule")
public class ClassScheduleController {
    @GetMapping("/test")
    public ClassScheduleResDto mockClassSchedule() {
        ClassScheduleResDto classScResDto = new ClassScheduleResDto();
        classScResDto.setClass_name("Spring Boot");
        classScResDto.setStart_date(LocalDate.of(2020,6,18));
        classScResDto.setEnd_date(LocalDate.of(2020,6,19));
        classScResDto.setClass_desc("JWT 서비스 구현하기");
        classScResDto.setClass_url("https://www.notion.so/JWT-c7a1d10d70e34dcdb30e4118c4fe88d9?pvs=25");
        return classScResDto;
    }
}
