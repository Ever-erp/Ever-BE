package com.example.autoever_1st.survey.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.survey.dto.req.SurveyCreateDto;
import com.example.autoever_1st.survey.dto.res.SurveyResDto;
import com.example.autoever_1st.survey.entities.Survey;
import com.example.autoever_1st.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/{uuid}")
    public ApiResponse<SurveyResDto> getSurvey(@PathVariable String uuid) {
        SurveyResDto surveyResDto = surveyService.getSurvey(uuid);
        return ApiResponse.success(surveyResDto, HttpStatus.OK.value());
    }

    @GetMapping("/page")
    public ApiResponse<Page<SurveyResDto>> getSurveysForAdmin(@RequestParam int page, @RequestParam int size, Authentication authentication) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<SurveyResDto> dtoPage = surveyService.getSurveyPage(pageable, authentication);
        return ApiResponse.success(dtoPage, HttpStatus.OK.value());
    }

//    public ApiResponse<Void> createSurvey(@RequestBody SurveyCreateDto surveyCreateDto) { // 보류
//        surveyService.createSurvey(surveyCreateDto);
//        return ApiResponse.success(null, HttpStatus.OK.value());
//    }
}
