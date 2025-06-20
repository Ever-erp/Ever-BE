package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class ClassesResDto {
    private Long id;
    private String name;        // 사용자 이름
    private int cohort;         // 인원
}
