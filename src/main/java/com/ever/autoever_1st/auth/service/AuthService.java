package com.ever.autoever_1st.auth.service;

import com.ever.autoever_1st.auth.dto.req.ImageReqDto;
import com.ever.autoever_1st.auth.dto.res.LoginResponseDto;
import com.ever.autoever_1st.auth.dto.common.TokenDto;
import com.ever.autoever_1st.auth.dto.req.LoginReqDto;
import com.ever.autoever_1st.auth.dto.req.MemberReqDto;
import com.ever.autoever_1st.auth.dto.req.TokenReqDto;
import com.ever.autoever_1st.auth.dto.res.MemberResponseDto;
import org.springframework.security.core.Authentication;

public interface AuthService {
    MemberResponseDto signup(MemberReqDto memberReqDto);
    LoginResponseDto login(LoginReqDto loginReqDto);
    TokenDto reissue(TokenReqDto tokenReqDto);
    void deactivate(Authentication authentication);
    void logout(Authentication authentication);
    void updateProfileImage(ImageReqDto imageReqDto);
}
