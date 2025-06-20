package com.example.autoever_1st.example_domain.service;

import com.example.autoever_1st.example_domain.dto.common.TokenDto;
import com.example.autoever_1st.example_domain.dto.req.LoginReqDto;
import com.example.autoever_1st.example_domain.dto.req.MemberReqDto;
import com.example.autoever_1st.example_domain.dto.req.TokenReqDto;
import com.example.autoever_1st.example_domain.dto.res.MemberResponseDto;
import org.springframework.security.core.Authentication;

public interface AuthService {
    MemberResponseDto signup(MemberReqDto memberReqDto);
    TokenDto login(LoginReqDto loginReqDto);
    TokenDto reissue(TokenReqDto tokenReqDto);
    void deactivate(Authentication authentication);
    void logout(Authentication authentication);
}
