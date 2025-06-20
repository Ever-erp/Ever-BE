package com.example.autoever_1st.domain.service.impl;

import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.example.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.example.autoever_1st.example_domain.dto.common.TokenDto;
import com.example.autoever_1st.example_domain.dto.req.LoginReqDto;
import com.example.autoever_1st.example_domain.dto.req.MemberReqDto;
import com.example.autoever_1st.example_domain.dto.req.TokenReqDto;
import com.example.autoever_1st.example_domain.dto.res.MemberResponseDto;
import com.example.autoever_1st.domain.entities.Member;
import com.example.autoever_1st.domain.entities.RefreshToken;
import com.example.autoever_1st.domain.repository.MemberRepository;
import com.example.autoever_1st.domain.repository.RefreshTokenRepository;
import com.example.autoever_1st.domain.service.AuthService;
import com.example.autoever_1st.jwt.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder managerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder, AuthenticationManagerBuilder managerBuilder,
                           JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.managerBuilder = managerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public MemberResponseDto signup(MemberReqDto memberReqDto) {
        if(!memberReqDto.getPwd().equals(memberReqDto.getPwdCheck())) {
            throw new ValidationException("비밀번호와 비밀번호 확인이 일치하지 않습니다.", CustomStatus.INVALID_INPUT.getStatus());
        }
        if (memberRepository.existsByEmail(memberReqDto.getEmail())) {
            throw new ValidationException("이미 사용 중인 이메일입니다.", CustomStatus.INVALID_INPUT);
        }
        if (!memberReqDto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("유효하지 않은 이메일 형식입니다.", CustomStatus.INVALID_INPUT);
        }

        Member newMember = Member.builder()
                .email(memberReqDto.getEmail())
                .pwd(passwordEncoder.encode(memberReqDto.getPwd()))
                .name(memberReqDto.getName())
                .birth(memberReqDto.getBirth())
                .gender(memberReqDto.getGender())
                .phone(memberReqDto.getPhone())
                .address(memberReqDto.getAddress())
                .profileImage(memberReqDto.getProfileImage())
                .build();
        Member saved = memberRepository.save(newMember);

        return MemberResponseDto.of(saved);
    }

    @Override
    public TokenDto login(LoginReqDto loginReqDto) {
        Member member = memberRepository.findByEmail(loginReqDto.getEmail())
                .orElseThrow(() -> new DataNotFoundException("해당 이메일로 가입된 사용자가 없습니다.", CustomStatus.NOT_HAVE_DATA));
        // 비활성화 여부 확인
        if (!member.isActive()) {
            throw new ValidationException("비활성화된 계정입니다.", CustomStatus.INVALID_INPUT);
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = loginReqDto.toAuthentication();
            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

            // JWT 발급
            TokenDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);
            String email = authentication.getName();

            // 기존 토큰이 있으면 삭제하고 새로 저장
            refreshTokenRepository.findByEmail(email)
                    .ifPresent(existing -> refreshTokenRepository.delete(existing));

            RefreshToken refreshToken = RefreshToken.builder()
                    .email(email)
                    .token(tokenDto.getRefreshToken())
                    .build();
            refreshTokenRepository.save(refreshToken);

            return tokenDto;
        } catch (BadCredentialsException e) {
            throw new ValidationException("이메일 또는 비밀번호가 일치하지 않습니다.", CustomStatus.INVALID_INPUT);
        }
    }

    @Override
    public TokenDto reissue(TokenReqDto tokenReqDto) {
        if (!jwtTokenProvider.validateToken(tokenReqDto.getRefreshToken())) {
            throw new ValidationException("Refresh Token이 유효하지 않습니다.", CustomStatus.INVALID_INPUT);
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(tokenReqDto.getAccessToken());

        RefreshToken refreshToken = refreshTokenRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new DataNotFoundException("로그아웃된 사용자입니다.", CustomStatus.NOT_HAVE_DATA));

        if (!refreshToken.getToken().equals(tokenReqDto.getRefreshToken())) {
            throw new ValidationException("토큰 정보가 일치하지 않습니다.", CustomStatus.INVALID_INPUT);
        }

        TokenDto tokenDto = jwtTokenProvider.generateTokenDto(authentication);

        refreshToken.setToken(tokenDto.getRefreshToken());
        refreshTokenRepository.save(refreshToken);

        return tokenDto;
    }

    @Transactional
    @Override
    public void deactivate(Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));

        member.deactivate();
    }

    @Transactional
    @Override
    public void logout(Authentication authentication) {
        String email = authentication.getName();
        refreshTokenRepository.findByEmail(email)
                .ifPresent(refreshTokenRepository::delete);
    }
}
