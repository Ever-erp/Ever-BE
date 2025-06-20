package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.example_domain.dto.res.SurveyResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})
@RequestMapping("/mock/survey")
public class SurveyController {

    private SurveyResDto testSurvey() {
        SurveyResDto dto = new SurveyResDto();
        dto.setSurveyTitle("2025 사내 설문조사");
        dto.setSurveyDesc("2025년 전사 대상 설문조사입니다.");
        dto.setDueDate(LocalDate.of(2025, 7, 1));
        dto.setQuestion(Arrays.asList("질문1: 만족도?", "질문2: 개선사항?", "질문3: 건의사항?"));
        dto.setAnswer(Arrays.asList("객관식 답변", 123, true)); // 예시로 섞어서 넣음
        return dto;
    }

    @GetMapping
    public ApiResponse<SurveyResDto> mockGetSurvey() {
        return ApiResponse.success(testSurvey(), 200);
    }

    @PostMapping
    public ApiResponse<SurveyResDto> mockPostSurvey(@RequestBody SurveyResDto surveyResDto) {
        // 실제 저장 로직 없으니 테스트용 더미 반환
        return ApiResponse.success(testSurvey(), 201);
    }

    @PutMapping
    public ApiResponse<SurveyResDto> mockUpdateSurvey(@RequestBody SurveyResDto surveyResDto) {
        SurveyResDto updated = testSurvey();
        updated.setSurveyTitle("업데이트 된 설문 제목");
        return ApiResponse.success(updated, 200);
    }

    @DeleteMapping
    public ApiResponse<SurveyResDto> mockDeleteSurvey() {
        return ApiResponse.success(null, 200);
    }
}
