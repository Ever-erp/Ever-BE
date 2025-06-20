package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.common.exception.exception_class.business.BusinessException;
import com.example.autoever_1st.example_domain.dto.res.NoticeResDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Fetch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//@Slf4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/notice")
public class NoticeController {

    private static NoticeResDto noticeResDto = new NoticeResDto(1L, "공지", "시스템 점검 안내", "관리자", "2025년 6월 30일 오전 2시부터 시스템 점검이 진행됩니다.", "guide.pdf", true);
    private static List<NoticeResDto> noticeResDtoList = new ArrayList<>();

    @PostConstruct
    public void init() {
        for(int i = 0; i < 42; i++){
            NoticeResDto noticeResDto;
            if(i % 10 != 0) {
                noticeResDto =  new NoticeResDto((long)i, "공지" + i, "시스템 점검 안내" + i,"관리자"+i, "2025년 6월 30일 오전 2시부터 시스템 점검이 진행됩니다." + i, "guide.pdf" + i, false);
            }else{
                noticeResDto = new NoticeResDto((long)i, "공지" + i, "시스템 점검 안내" + i,"관리자"+i, "2025년 6월 30일 오전 2시부터 시스템 점검이 진행됩니다." + i, "guide.pdf" + i, true);
            }
            noticeResDtoList.add(noticeResDto);
        }
    }

    private NoticeResDto createTestNotice(long id, boolean isPin) {
        NoticeResDto testDto = new NoticeResDto();
        testDto.setNoticeId(id);
        testDto.setType("공지");
        testDto.setTitle("시스템 점검 안내" + id);
        testDto.setWriter("관리자" + id);
        testDto.setContents("2025년 6월 30일 오전 2시부터 시스템 점검이 진행됩니다." + id);
        testDto.setFile("guide.pdf" + id);
        testDto.setPin(isPin);
        return testDto;
    }

    @GetMapping("/{id}")
    public ApiResponse<NoticeResDto> mockNoticeGet(
            @PathVariable("id") Long id) {
        return ApiResponse.success(noticeResDto, 200);
    }

    @GetMapping("/page")
    public ApiResponse<Page<NoticeResDto>> mockNoticePageGet(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        if(page < 1){
            throw new BusinessException("페이지는 0보다 커야 합니다", HttpStatus.BAD_REQUEST);
        }
        if(size > 10){
            throw new BusinessException("사이즈는 10보다 클 수 없습니다", HttpStatus.BAD_REQUEST);
        }

        PageRequest pageRequest = PageRequest.of(page - 1, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), noticeResDtoList.size());

        List<NoticeResDto> content = (start <= end)
                ? noticeResDtoList.subList(start, end)
                : Collections.emptyList();

        Page<NoticeResDto> pageRes = new PageImpl<>(content, pageRequest, noticeResDtoList.size());

        return ApiResponse.success(pageRes, 200);
    }

    @PostMapping
    public ApiResponse<NoticeResDto> mockNoticePost(/*@RequestBody NoticeResDto noticeResDto*/) {
        // 실제 저장은 안 하고 테스트용 응답만 리턴
        return ApiResponse.success(noticeResDto, 201);
    }

    @PatchMapping("/{id}")
    public ApiResponse<NoticeResDto> mockNoticeUpdate(/*@RequestBody NoticeResDto noticeResDto,*/ @PathVariable Long id) {
        NoticeResDto test = noticeResDto;
        test.setNoticeId(id);
        test.setTitle("업데이트된 공지사항 제목");
        test.setContents("내용도 업데이트되었습니다.");
        return ApiResponse.success(test, 200);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<NoticeResDto> mockNoticeDelete(@PathVariable Long id) {
        NoticeResDto test = noticeResDto;
        test.setNoticeId(id);
        test.setTitle("삭제된 공지사항");
        return ApiResponse.success(null, 200);
    }
}