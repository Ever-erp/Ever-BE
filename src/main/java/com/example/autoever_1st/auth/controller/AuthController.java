package com.example.autoever_1st.auth.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.example.autoever_1st.notice.dto.common.TokenDto;
import com.example.autoever_1st.notice.dto.req.LoginReqDto;
import com.example.autoever_1st.notice.dto.req.MemberReqDto;
import com.example.autoever_1st.notice.dto.req.TokenReqDto;
import com.example.autoever_1st.notice.dto.res.MemberResponseDto;
import com.example.autoever_1st.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
        if (memberReqDto.getEmail() == null || memberReqDto.getEmail().trim().isEmpty()) {
            throw new ValidationException("이메일은 필수 입력값입니다.", CustomStatus.INVALID_INPUT);
        }
        MemberResponseDto memberResponseDto = authService.signup(memberReqDto);
        return ApiResponse.success(memberResponseDto, HttpStatus.CREATED.value());
    }

    @PostMapping("/login")
    public ApiResponse<TokenDto> login(@RequestBody LoginReqDto loginReqDto) {
        TokenDto tokenDto = authService.login(loginReqDto);
        return ApiResponse.success(tokenDto, HttpStatus.OK.value());
    }

    @PostMapping("/reissue")
    public ApiResponse<TokenDto> reissue(@RequestBody TokenReqDto tokenReqDto) {
        TokenDto tokenDto = authService.reissue(tokenReqDto);
        return ApiResponse.success(tokenDto, HttpStatus.OK.value());
    }

    @PatchMapping("/deactivate")
    public ApiResponse<Void> deactivateMember(Authentication authentication) {
        authService.deactivate(authentication);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }

    @DeleteMapping("/logout")
    public ApiResponse<Void> logout(Authentication authentication) {
        authService.logout(authentication);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }
}
