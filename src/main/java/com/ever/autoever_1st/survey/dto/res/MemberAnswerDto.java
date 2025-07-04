package com.ever.autoever_1st.survey.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MemberAnswerDto {
    private Long memberId;
    private String memberName;
    private List<String> answer;
}
