package com.example.autoever_1st.auth.dto.res;

import com.example.autoever_1st.auth.dto.common.TokenDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private TokenDto tokenDto;
    private MemberResponseDto memberResponseDto;
}
