package com.example.autoever_1st.survey.service;

import com.example.autoever_1st.survey.dto.SurveySubmitDto;
import com.example.autoever_1st.survey.dto.req.SurveyCreateDto;
import com.example.autoever_1st.survey.dto.req.SurveyUpdateDto;
import com.example.autoever_1st.survey.dto.res.SurveyMemberResDto;
import com.example.autoever_1st.survey.dto.res.SurveyResDto;
import com.example.autoever_1st.survey.dto.res.SurveyWithMembersResDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface SurveyService {
    SurveyResDto getSurvey(String uuid, Authentication authentication);
    Page<SurveyResDto> getSurveyPage(String email, int page, int size);
    void createSurvey(SurveyCreateDto surveyCreateDto, String email);
    void submitSurvey(String email, String surveyId, SurveySubmitDto surveySubmitDto);
    void updateSurvey(String email, String surveyId, SurveyUpdateDto surveyUpdateDto);
    void updateSurveyAnswer(String email, String surveyId, SurveySubmitDto surveySubmitDto);
    void deleteSurvey(String email, String surveyId);
    void deleteSurveys(List<String> surveyIds, String email);
    SurveyMemberResDto getSurveyWithMember(String surveyId, String email);
    SurveyWithMembersResDto getSurveyWithMembers(String surveyId, String email);
}
