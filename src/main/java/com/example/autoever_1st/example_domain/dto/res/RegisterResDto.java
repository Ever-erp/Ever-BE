package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Date;

@Getter @Setter // @NoArgsConstructor => 가짜 데이터 위해 비활성화 (실제 데이터는 생성자 지우기)
public class RegisterResDto { // id
    private String email;   // 사용자 ID
    private String name;    // 사용자 이름
}
