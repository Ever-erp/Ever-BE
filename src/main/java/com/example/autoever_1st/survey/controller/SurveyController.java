package com.example.autoever_1st.survey.controller;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
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
    private final MemberRepository memberRepository;

    @GetMapping("/{uuid}")
    public ApiResponse<SurveyResDto> getSurvey(@PathVariable String uuid) {
        SurveyResDto surveyResDto = surveyService.getSurvey(uuid);
        return ApiResponse.success(surveyResDto, HttpStatus.OK.value());
    }

    @GetMapping("/page")
    public ApiResponse<Page<SurveyResDto>> getSurveyPage(@RequestParam int page, @RequestParam int size, Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));

        String role = member.getPosition().getRole();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<SurveyResDto> surveyPage;
        if ("관리자".equals(role)) {
            surveyPage = surveyService.getSurveyPageForAdmin(pageable);
        } else {
            surveyPage = surveyService.getSurveyPageForUser(member.getId(), pageable);
        }

        return ApiResponse.success(surveyPage, HttpStatus.OK.value());
    }
}
