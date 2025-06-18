package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.example_domain.dto.res.LoginResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
//@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/login")
public class LoginController {

    @GetMapping("/test")
    public LoginResDto mockLogin() {
        LoginResDto loginResDto = new LoginResDto();
        loginResDto.setEmail("login@test.com");
        loginResDto.setGrantType("Bearder");
        loginResDto.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImlhdCI6MTY4NzUwMDAwMCwiZXhwIjoxNjg3NTAzNjAwfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        loginResDto.setRefreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJtb2NrVXNlciIsImlhdCI6MTY4NzUxMDAwMCwiZXhwIjoxNjg3NTEzNjAwfQ.DummySignatureForTest12345");
        loginResDto.setAccessTokenExpireIn(1750231125197L);
        loginResDto.setRefreshTokenExpireIn(1750315725197L);
        return loginResDto;
    }
}

