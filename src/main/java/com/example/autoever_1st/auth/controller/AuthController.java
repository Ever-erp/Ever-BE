package com.example.autoever_1st.auth.controller;

import com.example.autoever_1st.auth.dto.res.LoginResponseDto;
import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.organization.entities.ClassEntity;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.example.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.example.autoever_1st.auth.dto.common.TokenDto;
import com.example.autoever_1st.auth.dto.req.LoginReqDto;
import com.example.autoever_1st.auth.dto.req.MemberReqDto;
import com.example.autoever_1st.auth.dto.req.TokenReqDto;
import com.example.autoever_1st.auth.dto.res.MemberResponseDto;
import com.example.autoever_1st.auth.service.AuthService;
import com.example.autoever_1st.organization.repository.ClassEntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final ClassEntityRepository classEntityRepository;

    public AuthController(AuthService authService, ClassEntityRepository classEntityRepository) {
        this.authService = authService;
        this.classEntityRepository = classEntityRepository;
    }

    @PostMapping("/signup")
    public ApiResponse<MemberResponseDto> signup(@RequestBody MemberReqDto memberReqDto) {
        if (memberReqDto.getEmail() == null || memberReqDto.getEmail().trim().isEmpty()) {
            throw new ValidationException("이메일은 필수 입력값입니다.", CustomStatus.INVALID_INPUT);
        }
        MemberResponseDto memberResponseDto = authService.signup(memberReqDto);
        return ApiResponse.success(memberResponseDto, HttpStatus.CREATED.value());
    }

    @GetMapping("/class/{classId}/cohort")
    public ApiResponse<Integer> getCohortByClass(@PathVariable Long classId) {
        ClassEntity classEntity = classEntityRepository.findById(classId)
                .orElseThrow(() -> new DataNotFoundException("반 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        return ApiResponse.success(classEntity.getCohort(), HttpStatus.OK.value());
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@RequestBody LoginReqDto loginReqDto) {
        LoginResponseDto loginResponseDto = authService.login(loginReqDto);
        return ApiResponse.success(loginResponseDto, HttpStatus.OK.value());
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
