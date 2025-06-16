package com.example.autoever_1st.example_domain.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TestRequestDto {
    private String title;
    private String content;

    public TestRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
