package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class SurveyResDto {
    private String surveyTitle;     // 설문 제목
    private String surveyDesc;      // 설문 설명
    private LocalDate dueDate;      // 설문 기한
    private List<String> question;  // 질문 모음
    private List<Object> answer;    // 답변 모음 ( 메타데이터 ex. String, int..) )
}
