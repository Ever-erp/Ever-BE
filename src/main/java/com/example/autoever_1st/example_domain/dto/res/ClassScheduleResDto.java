package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
public class ClassScheduleResDto {
    private String className;      // 과목
    private LocalDate startDate;   // 시작 날짜
    private LocalDate endDate;     // 종료 날짜
    private String classDesc;      // 과목 설명
    private String classUrl;       // 수업 자료(URL)
}
