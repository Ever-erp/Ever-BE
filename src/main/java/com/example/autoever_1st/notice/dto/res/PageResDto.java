package com.example.autoever_1st.notice.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PageResDto<T> {
    private final List<T> content;    // 현재 페이지의 데이터 리스트
    private final int pageNumber;     // 현재 페이지 번호 (0부터 시작)
    private final int pageSize;       // 페이지 크기
    private final long totalElements; // 전체 항목 수
    private final int totalPages;     // 전체 페이지 수
    private final boolean last;       // 마지막 페이지 여부
}