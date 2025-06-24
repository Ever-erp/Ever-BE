package com.example.autoever_1st.auth.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private String grantType;  // 인증 방식
    private String accessToken;  // 엑세스 토큰
    private String refreshToken;
    private Long tokenExpiresIn;
    private Long refreshExpireIn;
}
