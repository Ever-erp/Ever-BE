package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.example_domain.dto.res.LoginResDto;
import com.example.autoever_1st.example_domain.dto.res.RegisterResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
//@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/register")
public class RegisterController {
    @GetMapping("/test")
    public RegisterResDto mockRegister() {
        RegisterResDto registerResDto = new RegisterResDto();
        registerResDto.setEmail("register@test.com");
        registerResDto.setName("register");
        return registerResDto;
    }
}
