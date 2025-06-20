package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberSurveyResDto {
    private List<Object> answer;    // 답변 모음 ( 메타데이터 ex. String, int..) )
}
