package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter //@NoArgsConstructor
public class ClassScheduleResDto {
    private String class_name;      // 과목
    private LocalDate start_date;   // 시작 날짜
    private LocalDate end_date;     // 종료 날짜
    private String class_desc;      // 과목 설명
    private String class_url;       // 수업 자료(URL)
}
