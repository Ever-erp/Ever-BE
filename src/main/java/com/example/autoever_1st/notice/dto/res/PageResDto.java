package com.example.autoever_1st.notice.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResDto<T> {
    private List<T> content;   // 현재 페이지 데이터
    private int pageNumber;    // 현재 페이지 번호 (0부터 시작)
    private int pageSize;      // 페이지당 항목 수
    private long totalElements;// 전체 항목 수
    private int totalPages;    // 전체 페이지 수
    private boolean last;      // 마지막 페이지 여부
}