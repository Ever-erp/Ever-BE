package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.example_domain.dto.res.ClassesResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

//@Slf4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/classes")
public class ClassesController {
    @GetMapping
    public ClassesResDto mockClasses() {
        ClassesResDto classesResDto = new ClassesResDto();
        classesResDto.setId(1L);
        classesResDto.setName("웹&앱");
        classesResDto.setCohort(2);
        return classesResDto;
    }
}