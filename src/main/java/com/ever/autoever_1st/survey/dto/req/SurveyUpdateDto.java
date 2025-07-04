package com.ever.autoever_1st.survey.dto.req;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class SurveyUpdateDto {
    private String surveyTitle;
    private String surveyDesc;
    private LocalDate dueDate;
    private String status;
    private Integer surveySize;
    private List<String> surveyQuestion;
    private List<List<String>> surveyQuestionMeta;
    private String className;
}
