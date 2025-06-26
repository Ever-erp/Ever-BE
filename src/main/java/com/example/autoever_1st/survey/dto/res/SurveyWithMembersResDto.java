package com.example.autoever_1st.survey.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SurveyWithMembersResDto {
    private SurveyResDto survey;
    private List<MemberAnswerDto> members;
}
