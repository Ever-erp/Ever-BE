package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter // @NoArgsConstructor => 가짜 데이터 위해 비활성화 (실제 데이터는 생성자 지우기)
public class LoginResDto {
    private String email;               // 사용자 ID
    // 토큰 필드
    private String grantType;           // 인증 방식
    private String accessToken;         // 엑세스 토큰
    private String refreshToken;        // 리프레시 토큰
    private Long accessTokenExpireIn;   // 엑세스 토큰 만료 시간
    private Long refreshTokenExpireIn;  // 리프레시 토큰 만료 시간
}
