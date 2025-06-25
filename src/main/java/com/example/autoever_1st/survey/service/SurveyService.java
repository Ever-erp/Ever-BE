package com.example.autoever_1st.survey.service;

import com.example.autoever_1st.survey.dto.req.SurveyCreateDto;
import com.example.autoever_1st.survey.dto.res.SurveyResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface SurveyService {
    SurveyResDto getSurvey(String uuid);
    Page<SurveyResDto> getSurveyPageForAdmin(Pageable pageable);
    Page<SurveyResDto> getSurveyPageForUser(Long memberId, Pageable pageable);
}
