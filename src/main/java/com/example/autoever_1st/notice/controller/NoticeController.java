package com.example.autoever_1st.notice.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.notice.dto.req.NoticeWriteDto;
import com.example.autoever_1st.notice.dto.res.NoticeDto;
import com.example.autoever_1st.notice.model.SearchType;
import com.example.autoever_1st.notice.model.TargetRange;
import com.example.autoever_1st.notice.model.Type;
import com.example.autoever_1st.notice.service.impl.NoticeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    @Autowired
    private NoticeServiceImpl noticeServiceImpl;

    // 공지 작성
    @PostMapping
    public ApiResponse<NoticeDto> create(@RequestBody NoticeWriteDto dto, @RequestParam Long memberId) {
        return ApiResponse.success(noticeServiceImpl.createNotice(dto, memberId),201);
    }

    // 공지 글 번호(noticeId) 검색
    @GetMapping("/{id}")
    public ApiResponse<NoticeDto> get(@PathVariable Long id) {
        return ApiResponse.success(noticeServiceImpl.getNotice(id), 200);}

    // 공지 전체 조회 (페이징) 및 키워드 조회 (제목/내용/작성자)
    @GetMapping
    public ApiResponse<Page<NoticeDto>> list(
            // 조건 조회
            // 글 유형 카테고리(enum) : title(제목), contents(내용), writer(작성자)
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String text,

            // 페이지 조회
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("isPinned").descending().and(Sort.by("noticeId").descending()));
        Page<NoticeDto> result = (searchType != null && text != null && !text.isBlank())
                ? noticeServiceImpl.searchNotices(searchType, text, pageable)
                : noticeServiceImpl.getAllNotices(pageable);

        return ApiResponse.success(result,200);
    }
    // 공지 유형 (공개범위/구분)으로 조회(AND, OR)
    @GetMapping("/search")
    public ApiResponse<Page<NoticeDto>> searchByTarget(
            @RequestParam(required = false) TargetRange targetRange,
            @RequestParam(required = false) Type type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (targetRange == null) {
            targetRange = TargetRange.ALL_TARGETRANGE;
        }
        if (type == null) {
            type = Type.ALL_TYPE;
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("isPinned").descending().and(Sort.by("noticeId").descending()));
        return ApiResponse.success(noticeServiceImpl.searchByTargetRangeAndType(targetRange, type, pageable),200);
    }
    // 공지 수정(PATCH)
    @PatchMapping("/{id}")
    public ApiResponse<NoticeDto> patchUpdate(@PathVariable Long id, @RequestBody NoticeWriteDto dto) {
        return ApiResponse.success(noticeServiceImpl.updateNoticePartial(id, dto),200);
    }
    // 공지 수정(PUT)
    @PutMapping("/{id}")
    public ApiResponse<NoticeDto> update(@PathVariable Long id, @RequestBody NoticeWriteDto dto) {
        return ApiResponse.success(noticeServiceImpl.updateNotice(id, dto),200);
    }
    // 공지 삭제
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        noticeServiceImpl.deleteNotice(id);
        return ApiResponse.success(null,200); // 또는 message 담아도 됨
    }
}
