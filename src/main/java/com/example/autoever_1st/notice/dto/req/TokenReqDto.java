package com.example.autoever_1st.notice.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenReqDto {
    private String accessToken; // 만료된 accessToken
    private String refreshToken; // 저장된 refreshToken

    public TokenReqDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
