package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.example_domain.dto.res.LoginResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Slf  4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/login")
public class LoginController {

    private LoginResDto testLoginResDto(){
        // 초기값
        LoginResDto testDto = new LoginResDto();
        testDto.setEmail("login@test.com");
        testDto.setGrantType("Bearer");
        testDto.setAccessToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImlhdCI6MTY4NzUwMDAwMCwiZXhwIjoxNjg3NTAzNjAwfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        testDto.setRefreshToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJtb2NrVXNlciIsImlhdCI6MTY4NzUxMDAwMCwiZXhwIjoxNjg3NTEzNjAwfQ.DummySignatureForTest12345");
        testDto.setAccessTokenExpireIn(1750231125197L);
        testDto.setRefreshTokenExpireIn(1750315725197L);
        return testDto;
    }

    @PostMapping
    public ApiResponse<LoginResDto> mockLoginPost(/*@RequestParam LoginResDto loginResDto*/) {
        return ApiResponse.success(testLoginResDto(), 200);
    }
}

