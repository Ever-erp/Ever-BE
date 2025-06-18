package com.example.autoever_1st.example_domain.dto.res;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor => 가짜 데이터 위해 비활성화 (실제 데이터는 생성자 지우기)
public class ClassesResDto {
    private String name;        // 사용자 이름
    private int cohort;         // 인원
}
