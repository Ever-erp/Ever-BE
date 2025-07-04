package com.ever.autoever_1st.notice.controller;

import com.ever.autoever_1st.common.dto.response.ApiResponse;
import com.ever.autoever_1st.notice.constant.SearchType;
import com.ever.autoever_1st.notice.constant.TargetRange;
import com.ever.autoever_1st.notice.constant.Type;
import com.ever.autoever_1st.notice.dto.req.NoticeWriteDto;
import com.ever.autoever_1st.notice.dto.res.NoticeDto;
import com.ever.autoever_1st.notice.service.NoticeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notices")
@RequiredArgsConstructor
@Validated
public class NoticeController {

    private final NoticeService noticeService;

    // 공지 작성
    @PostMapping
    public ApiResponse<NoticeDto> create(@RequestBody @Valid NoticeWriteDto dto, Authentication authentication) {
        return ApiResponse.success(noticeService.createNotice(dto, authentication), HttpStatus.CREATED.value());
    }

    // 공지 글 번호(Id) 검색
    @GetMapping("/{id}")
    public ApiResponse<NoticeDto> get(@PathVariable Long id, Authentication authentication) {
        return ApiResponse.success(noticeService.getNotice(id, authentication), HttpStatus.OK.value());
    }

    // 공지 전체 조회 (페이징) 및 키워드 조회 (제목/내용/작성자)
    @GetMapping
    public ApiResponse<Page<NoticeDto>> list(
            // 조건 조회
            // 글 유형 카테고리(enum) : title(제목), contents(내용), writer(작성자)
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String text,
            // 페이지 조회
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("isPinned").descending().and(Sort.by("id").descending()));
        Page<NoticeDto> result = (searchType != null && text != null && !text.isBlank())
                ? noticeService.searchNotices(searchType, text, pageable,authentication)
                : noticeService.getAllNotices(pageable,authentication);

        return ApiResponse.success(result, HttpStatus.OK.value());
    }

    // 공지 수정(PUT)
    @PatchMapping("/{id}")
    public ApiResponse<NoticeDto> update(@PathVariable Long id, @RequestBody @Valid NoticeWriteDto dto, Authentication authentication) {
        return ApiResponse.success(noticeService.updateNotice(id, dto, authentication), HttpStatus.OK.value());
    }

    // 공지 삭제
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete (@PathVariable Long id, Authentication authentication){
        noticeService.deleteNotice(id, authentication);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }
}