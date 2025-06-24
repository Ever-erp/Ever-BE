package com.example.autoever_1st.survey.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.survey.dto.req.SurveyCreateDto;
import com.example.autoever_1st.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    public ApiResponse<Void> createSurvey(@RequestBody SurveyCreateDto surveyCreateDto) {
        surveyService.createSurvey(surveyCreateDto);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }
}
