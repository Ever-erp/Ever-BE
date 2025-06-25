package com.example.autoever_1st.survey.dto.res;

import com.example.autoever_1st.survey.entities.Survey;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class SurveyResDto {
    private String surveyId; // uuid
    private String surveyTitle;
    private String surveyDesc;
    private String status;
    private LocalDateTime createdAt;
    private LocalDate dueDate;
    private Integer surveySize;
    private List<String> surveyQuestion;
    private List<List<String>> surveyQuestionMeta;

    public static SurveyResDto toDto(Survey survey) {
        List<String> questions = List.of(survey.getQuestion().split(","));

        List<List<String>> metaList = Arrays.stream(survey.getQuestionMeta().split(","))
                .map(meta -> meta.equals("주관식") ? List.of("주관식") : Arrays.asList(meta.split("/")))
                .collect(Collectors.toList());

        return SurveyResDto.builder()
                .surveyId(survey.getUuid())
                .surveyTitle(survey.getTitle())
                .surveyDesc(survey.getDescription())
                .status(survey.getStatus())
                .createdAt(survey.getRegistedAt())
                .dueDate(survey.getDueDate())
                .surveySize(questions.size())
                .surveyQuestion(questions)
                .surveyQuestionMeta(metaList)
                .build();
    }
}
