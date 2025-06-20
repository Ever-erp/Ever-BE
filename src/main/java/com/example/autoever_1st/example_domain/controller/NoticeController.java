package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.example_domain.dto.res.NoticeResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.springframework.web.bind.annotation.*;

//@Slf4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/notice")
public class NoticeController {
    private NoticeResDto testNotice() {
        NoticeResDto testDto = new NoticeResDto();
        testDto.setNoticeId(1L);
        testDto.setType("공지");
        testDto.setTitle("시스템 점검 안내");
        testDto.setWriter("관리자");
        testDto.setContents("2025년 6월 30일 오전 2시부터 시스템 점검이 진행됩니다.");
        testDto.setFile("guide.pdf");
        testDto.setPin(true);
        return testDto;
    }

    @GetMapping
    public ApiResponse<NoticeResDto> mockNoticeGet() {
        return ApiResponse.success(testNotice(), 200);
    }

    @PostMapping
    public ApiResponse<NoticeResDto> mockNoticePost(/*@RequestBody NoticeResDto noticeResDto*/) {
        // 실제 저장은 안 하고 테스트용 응답만 리턴
        return ApiResponse.success(testNotice(), 201);
    }

    @PatchMapping("/{id}")
    public ApiResponse<NoticeResDto> mockNoticeUpdate(/*@RequestBody NoticeResDto noticeResDto,*/ @PathVariable Long id) {
        NoticeResDto test = testNotice();
        test.setNoticeId(id);
        test.setTitle("업데이트된 공지사항 제목");
        test.setContents("내용도 업데이트되었습니다.");
        return ApiResponse.success(test, 200);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<NoticeResDto> mockNoticeDelete(@PathVariable Long id) {
        NoticeResDto test = testNotice();
        test.setNoticeId(id);
        test.setTitle("삭제된 공지사항");
        return ApiResponse.success(null, 200);
    }
}