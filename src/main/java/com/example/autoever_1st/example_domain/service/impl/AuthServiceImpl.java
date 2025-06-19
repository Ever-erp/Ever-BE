package com.example.autoever_1st.example_domain.service.impl;

import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.example.autoever_1st.example_domain.dto.common.TokenDto;
import com.example.autoever_1st.example_domain.dto.req.LoginReqDto;
import com.example.autoever_1st.example_domain.dto.req.MemberReqDto;
import com.example.autoever_1st.example_domain.dto.res.MemberResponseDto;
import com.example.autoever_1st.example_domain.entities.Member;
import com.example.autoever_1st.example_domain.repository.MemberRepository;
import com.example.autoever_1st.example_domain.service.AuthService;
import com.example.autoever_1st.jwt.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder managerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder,
                           AuthenticationManagerBuilder managerBuilder, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.managerBuilder = managerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public MemberResponseDto signup(MemberReqDto memberReqDto) {
        if(!memberReqDto.getPwd().equals(memberReqDto.getPwdCheck())) {
            throw new ValidationException("비밀번호와 비밀번호 확인이 일치하지 않습니다.", CustomStatus.INVALID_INPUT.getStatus());
        }
        if (memberRepository.existsByEmail(memberReqDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
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
        UsernamePasswordAuthenticationToken authenticationToken = loginReqDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.generateTokenDto(authentication);
    }
}
