package com.ever.autoever_1st.auth.controller;

import com.ever.autoever_1st.auth.dto.req.ImageReqDto;
import com.ever.autoever_1st.auth.dto.res.LoginResponseDto;
import com.ever.autoever_1st.common.dto.response.ApiResponse;
import com.ever.autoever_1st.common.exception.CustomStatus;
import com.ever.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.ever.autoever_1st.jwt.JwtTokenProvider;
import com.ever.autoever_1st.organization.repository.ClassEntityRepository;
import com.ever.autoever_1st.organization.dto.res.ClassSimpleDto;
import com.ever.autoever_1st.auth.dto.common.TokenDto;
import com.ever.autoever_1st.auth.dto.req.LoginReqDto;
import com.ever.autoever_1st.auth.dto.req.MemberReqDto;
import com.ever.autoever_1st.auth.dto.req.TokenReqDto;
import com.ever.autoever_1st.auth.dto.res.MemberResponseDto;
import com.ever.autoever_1st.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;
    private final ClassEntityRepository classEntityRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthService authService, ClassEntityRepository classEntityRepository, JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.classEntityRepository = classEntityRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/signup")
    public ApiResponse<MemberResponseDto> signup(@RequestBody MemberReqDto memberReqDto) {
        if (memberReqDto.getEmail() == null || memberReqDto.getEmail().trim().isEmpty()) {
            throw new ValidationException("이메일은 필수 입력값입니다.", CustomStatus.INVALID_INPUT);
        }
        MemberResponseDto memberResponseDto = authService.signup(memberReqDto);
        return ApiResponse.success(memberResponseDto, HttpStatus.CREATED.value());
    }

    @PostMapping("/updateImage")
    public ApiResponse<Void> updateProfileImage(@RequestBody ImageReqDto imageReqDto) {
        authService.updateProfileImage(imageReqDto);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }

    @GetMapping("/class/all")
    public ApiResponse<List<ClassSimpleDto>> getClassInfoByName() {
         List<ClassSimpleDto> result = classEntityRepository.findAll()
                .stream()
                .map(classEntity -> new ClassSimpleDto(
                        classEntity.getName(),
                        classEntity.getCohort()
                ))
                .toList();
        return ApiResponse.success(result, HttpStatus.OK.value());
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

    @GetMapping("/validate")
    public ApiResponse<Void> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = extractToken(authHeader);
            if (!jwtTokenProvider.validateToken(token)) {
                throw new ValidationException("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
            }
            return ApiResponse.success(null, HttpStatus.OK.value());
        } catch (Exception e) {
            throw new ValidationException("토큰 검증 중 오류 발생", HttpStatus.UNAUTHORIZED);
        }
    }
    private String extractToken(String header) {
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new ValidationException("Authorization 헤더 형식이 잘못되었습니다.", HttpStatus.UNAUTHORIZED);
    }
}
