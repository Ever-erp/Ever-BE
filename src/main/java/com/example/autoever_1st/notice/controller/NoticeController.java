package com.example.autoever_1st.notice.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.notice.dto.res.NoticeResDto;
import com.example.autoever_1st.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RequestMapping("/mock/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/{id}")
    public ApiResponse<NoticeResDto> getNotice(@PathVariable Long id) {
        return ApiResponse.success(noticeService.findById(id), 200);
    }

    @GetMapping("/pinned")
    public ApiResponse<List<NoticeResDto>> getPinnedNotices() {
        return ApiResponse.success(noticeService.findPinnedNotices(), 200);
    }

    @GetMapping
    public ApiResponse<PageResDto<NoticeResDto>> getNotices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String text) {
        return ApiResponse.success(noticeService.getNotices(page, size, type, text), 200);
    }

}