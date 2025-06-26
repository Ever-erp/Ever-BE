package com.example.autoever_1st.survey.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SurveyMemberResDto {
    private SurveyResDto survey;
    private MemberAnswerDto member;
}

