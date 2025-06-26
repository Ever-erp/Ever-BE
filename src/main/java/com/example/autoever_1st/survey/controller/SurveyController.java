package com.example.autoever_1st.survey.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.example.autoever_1st.survey.dto.SurveySubmitDto;
import com.example.autoever_1st.survey.dto.req.SurveyCreateDto;
import com.example.autoever_1st.survey.dto.req.SurveyDeleteDto;
import com.example.autoever_1st.survey.dto.req.SurveyUpdateDto;
import com.example.autoever_1st.survey.dto.res.SurveyMemberResDto;
import com.example.autoever_1st.survey.dto.res.SurveyResDto;
import com.example.autoever_1st.survey.dto.res.SurveyWithMembersResDto;
import com.example.autoever_1st.survey.entities.Survey;
import com.example.autoever_1st.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;

    @GetMapping("/{uuid}")
    public ApiResponse<SurveyResDto> getSurvey(@PathVariable String uuid, Authentication authentication) {
        return ApiResponse.success(surveyService.getSurvey(uuid, authentication), HttpStatus.OK.value());
    }

    @GetMapping("/page")
    public ApiResponse<Page<SurveyResDto>> getSurveyPage(@RequestParam int page, @RequestParam int size, Authentication authentication) {
        String email = authentication.getName();
        Page<SurveyResDto> surveyPage = surveyService.getSurveyPage(email, page, size);
        return ApiResponse.success(surveyPage, HttpStatus.OK.value());
    }

    @PostMapping
    public ApiResponse<Void> createSurvey(@RequestBody SurveyCreateDto surveyCreateDto, Authentication authentication) {
        String email = authentication.getName();
        surveyService.createSurvey(surveyCreateDto, email);
        return ApiResponse.success(null, HttpStatus.CREATED.value());
    }

    @PostMapping("/{surveyId}/submit")
    public ApiResponse<Void> submitSurvey(@PathVariable String surveyId, @RequestBody SurveySubmitDto surveySubmitDto,
                                            Authentication authentication) {
        String email = authentication.getName();
        surveyService.submitSurvey(email, surveyId, surveySubmitDto);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }

    @PatchMapping("/{surveyId}")
    public ApiResponse<Void> updateSurvey(@PathVariable String surveyId, @RequestBody SurveyUpdateDto surveyUpdateDto,
                                            Authentication authentication) {
        String email = authentication.getName();
        surveyService.updateSurvey(email, surveyId, surveyUpdateDto);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }

    @PatchMapping("/{surveyId}/submit")
    public ApiResponse<Void> updateSurveyAnswer (@PathVariable String surveyId, @RequestBody SurveySubmitDto surveySubmitDto,
                                                 Authentication authentication) {
        String email = authentication.getName();
        surveyService.updateSurveyAnswer(email, surveyId, surveySubmitDto);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }

    @DeleteMapping("/{surveyId}")
    public ApiResponse<Void> deleteSurvey(@PathVariable String surveyId, Authentication authentication) {
        String email = authentication.getName();
        surveyService.deleteSurvey(email, surveyId);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }

    @DeleteMapping("/multiple")
    public ApiResponse<Void> deleteSurveys(@RequestBody SurveyDeleteDto surveyDeleteDto, Authentication authentication) {
        String email = authentication.getName();
        surveyService.deleteSurveys(surveyDeleteDto.getSurveyIds(), email);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }

    @GetMapping("/{surveyId}/user")
    public ApiResponse<SurveyMemberResDto> getSurveyWithMember(@PathVariable String surveyId, Authentication authentication) {
        String email = authentication.getName();
        SurveyMemberResDto surveyMemberResDto = surveyService.getSurveyWithMember(surveyId, email);
        return ApiResponse.success(surveyMemberResDto, HttpStatus.OK.value());
    }

    @GetMapping("/{surveyId}/members")
    public ApiResponse<SurveyWithMembersResDto> getSurveyWithMembers(@PathVariable String surveyId, Authentication authentication) {
        String email = authentication.getName();
        SurveyWithMembersResDto surveyWithMembers = surveyService.getSurveyWithMembers(surveyId, email);
        return ApiResponse.success(surveyWithMembers, HttpStatus.OK.value());
    }

}
