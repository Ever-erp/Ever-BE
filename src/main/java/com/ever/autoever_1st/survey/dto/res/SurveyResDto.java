package com.ever.autoever_1st.survey.dto.res;

import com.ever.autoever_1st.common.exception.CustomStatus;
import com.ever.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.ever.autoever_1st.survey.entities.Survey;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class SurveyResDto {
    private String surveyId; // uuid
    private String surveyTitle;
    private String surveyDesc;
    private String status;
    private LocalDate createdAt;
    private LocalDate dueDate;
    private Integer surveySize;
    private List<String> surveyQuestion;
    private List<List<String>> surveyQuestionMeta;
    private String className;
    private int answeredCount;
    private int classTotalMemberCount;
    private List<String> surveyAnswer;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static SurveyResDto toDto(Survey survey, String className, int answeredCount, int totalMemberCount) {
        List<String> questions = parseQuestions(survey.getQuestion());
        List<List<String>> metaList = parseQuestionMeta(survey.getQuestionMeta());

        return SurveyResDto.builder()
                .surveyId(survey.getUuid())
                .surveyTitle(survey.getTitle())
                .surveyDesc(survey.getDescription())
                .status(survey.getStatus())
                .createdAt(survey.getRegistedAt().toLocalDate())
                .dueDate(survey.getDueDate())
                .surveySize(questions.size())
                .surveyQuestion(questions)
                .surveyQuestionMeta(metaList)
                .className(className)
                .answeredCount(answeredCount)
                .classTotalMemberCount(totalMemberCount)
                .build();
    }

    public static SurveyResDto withAnswer(Survey survey, String className, int answeredCount, int totalMemberCount, List<String> answers) {
        SurveyResDto surveyResDto = toDto(survey, className, answeredCount, totalMemberCount);
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
