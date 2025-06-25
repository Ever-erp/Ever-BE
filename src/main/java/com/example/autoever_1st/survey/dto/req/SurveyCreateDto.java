package com.example.autoever_1st.survey.dto.req;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SurveyCreateDto {
    private String title;
    private String className;
    private int cohort;
    private String question;
    private String questionMeta;
    private LocalDate postDate;
}
