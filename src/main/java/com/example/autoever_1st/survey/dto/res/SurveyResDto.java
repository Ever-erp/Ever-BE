package com.example.autoever_1st.survey.dto.res;

import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.example.autoever_1st.survey.entities.Survey;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
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
    private List<String> surveyAnswer;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static SurveyResDto toDto(Survey survey) {
        List<String> questions = parseQuestions(survey.getQuestion());
        List<List<String>> metaList = parseQuestionMeta(survey.getQuestionMeta());

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

    public static SurveyResDto withAnswer(Survey survey, List<String> answers) {
        SurveyResDto surveyResDto = toDto(survey);
        surveyResDto.surveyAnswer = answers;
        return surveyResDto;
    }

    private static List<String> parseQuestions(String questions) {
        try {
            return objectMapper.readValue(questions, new TypeReference<>() {});
        } catch (Exception e) {
            throw new ValidationException("파싱 실패", CustomStatus.INVALID_INPUT);
        }
    }

    private static List<List<String>> parseQuestionMeta(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            throw new ValidationException("파싱 실패", CustomStatus.INVALID_INPUT);
        }
    }
}
