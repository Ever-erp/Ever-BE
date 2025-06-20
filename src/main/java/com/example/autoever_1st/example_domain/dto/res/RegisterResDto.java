package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class RegisterResDto {
    private String email;   // 사용자 ID
    private String name;    // 사용자 이름
}
