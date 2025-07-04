package com.ever.autoever_1st.notice.dto.common;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageDto<T> {
    // 페이징 결과 데이터
    private final List<T> content;        // 현재 페이지의 데이터 리스트
    private final int pageNumber;         // 현재 페이지 번호 (0부터 시작)
    private final int pageSize;           // 페이지 크기
    // 페이징 메타 데이터
    private final long totalElements;     // 전체 데이터 개수
    private final int totalPages;         // 전체 페이지 수
    private final boolean last;           // 마지막 페이지 여부

    public PageDto(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
    }
}
