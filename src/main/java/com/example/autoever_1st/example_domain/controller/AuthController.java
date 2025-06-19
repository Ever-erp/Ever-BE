package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.example.autoever_1st.example_domain.dto.common.TokenDto;
import com.example.autoever_1st.example_domain.dto.req.LoginReqDto;
import com.example.autoever_1st.example_domain.dto.req.MemberReqDto;
import com.example.autoever_1st.example_domain.dto.res.MemberResponseDto;
import com.example.autoever_1st.example_domain.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ApiResponse<MemberResponseDto> signup(@RequestBody MemberReqDto memberReqDto) {
        MemberResponseDto memberResponseDto = authService.signup(memberReqDto);
        return ApiResponse.success(memberResponseDto, HttpStatus.CREATED.value());
    }

    @PostMapping("/login")
    public ApiResponse<TokenDto> login(@RequestBody LoginReqDto loginReqDto) {
        TokenDto tokenDto = authService.login(loginReqDto);
        return ApiResponse.success(tokenDto, HttpStatus.OK.value());
    }

}
