package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.example_domain.dto.res.MemberSurveyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})
@RequestMapping("/mock/member-survey")
public class MemberSurveyController {

    private MemberSurveyResDto testMemberSurvey() {
        MemberSurveyResDto dto = new MemberSurveyResDto();
        dto.setAnswer(Arrays.asList("응답1", Arrays.asList("복수응답1", "복수응답2"), 42));
        return dto;
    }

    @GetMapping
    public ApiResponse<MemberSurveyResDto> mockGetMemberSurvey() {
        return ApiResponse.success(testMemberSurvey(), 200);
    }

    @PostMapping
    public ApiResponse<MemberSurveyResDto> mockPostMemberSurvey(/*@RequestBody MemberSurveyResDto memberSurveyResDto*/) {
        return ApiResponse.success(testMemberSurvey(), 201);
    }

    @PutMapping
    public ApiResponse<MemberSurveyResDto> mockUpdateMemberSurvey(/*@RequestBody MemberSurveyResDto memberSurveyResDto*/) {
        MemberSurveyResDto test = testMemberSurvey();
        test.setAnswer(Arrays.asList("업데이트응답1", Arrays.asList("업데이트복수응답1", "업데이트복수응답2"), 42));
        return ApiResponse.success(test, 200);
    }

    @DeleteMapping
    public ApiResponse<MemberSurveyResDto> mockDeleteMemberSurvey() {
        return ApiResponse.success(null, 200);
    }
}
