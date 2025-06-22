package com.example.autoever_1st.notice.controller;

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

    @PostMapping
    public NoticeDto create(@RequestBody NoticeWriteDto dto, @RequestParam Long memberId) {
        return noticeServiceImpl.createNotice(dto, memberId);
    }

    @GetMapping("/{id}")
    public NoticeDto get(@PathVariable Long id) {
        return noticeServiceImpl.getNotice(id);
    }

    @GetMapping
    public Page<NoticeDto> list(
            // 조건 조회
            // 글 유형 카테고리(enum) : title(제목), contents(내용), writer(작성자)
            @RequestParam(required = false) SearchType searchType,
            @RequestParam(required = false) String text,

            // 페이지 조회
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("isPinned").descending().and(Sort.by("noticeId").descending()));
        if (searchType != null && text != null && !text.isBlank()) {
            return noticeServiceImpl.searchNotices(searchType, text, pageable);
        } else {
            return noticeServiceImpl.getAllNotices(pageable);
        }
    }

    @GetMapping("/search")
    public Page<NoticeDto> searchByTarget(
            @RequestParam(required = false, defaultValue = "ALL_TARGETRANGE") TargetRange targetRange,
            @RequestParam(required = false, defaultValue = "ALL_TYPE") Type type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("isPinned").descending().and(Sort.by("noticeId").descending()));
        return noticeServiceImpl.searchByTargetRangeAndType(targetRange, type, pageable);
    }

    @PatchMapping("/{id}")
    public NoticeDto patchUpdate(@PathVariable Long id, @RequestBody NoticeWriteDto dto) {
        return noticeServiceImpl.updateNoticePartial(id, dto);
    }

    @PutMapping("/{id}")
    public NoticeDto update(@PathVariable Long id, @RequestBody NoticeWriteDto dto) {
        return noticeServiceImpl.updateNotice(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        noticeServiceImpl.deleteNotice(id);
    }
}
