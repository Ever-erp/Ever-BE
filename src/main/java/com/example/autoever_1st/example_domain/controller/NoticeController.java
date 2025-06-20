package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.example_domain.dto.res.NoticeResDto;
import com.example.autoever_1st.example_domain.service.NoticeService;
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
}