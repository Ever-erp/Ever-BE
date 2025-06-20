package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PositionResDto {
    private String role;        // 역할(권한) : (관리자/강사/일반)
}
