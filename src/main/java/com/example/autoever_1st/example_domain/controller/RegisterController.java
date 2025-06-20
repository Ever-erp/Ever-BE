package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.example_domain.dto.res.LoginResDto;
import com.example.autoever_1st.example_domain.dto.res.RegisterResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

//@Slf4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/register")
public class RegisterController {

    private RegisterResDto testRegisterResDto() {
        // 초기값
        RegisterResDto testDto = new RegisterResDto();
        testDto.setEmail("register@test.com");
        testDto.setName("register");
        return testDto;
    }

    @PostMapping
    public ApiResponse<RegisterResDto> mockRegister(@RequestBody RegisterResDto registerResDto) {
        return ApiResponse.success(testRegisterResDto(), 201);
    }
}
