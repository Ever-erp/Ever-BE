package com.example.autoever_1st.notice.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@NoArgsConstructor
@Getter
public class LoginReqDto {
    private String email;
    private String pwd;
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, pwd);
    }
}
