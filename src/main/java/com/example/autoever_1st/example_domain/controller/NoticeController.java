package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.example_domain.dto.res.NoticeResDto;
import com.example.autoever_1st.example_domain.dto.res.NoticeResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
//@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/notice")
public class NoticeController {
    @GetMapping("/test")
    public NoticeResDto mockRegister() {
        NoticeResDto noticeResDto = new NoticeResDto();
        noticeResDto.setNotice_id(1);
        noticeResDto.setType("공지사항");
        noticeResDto.setTitle("공지 제목");
        noticeResDto.setWriter("작성자");
        noticeResDto.setContents("공지 내용");
        noticeResDto.setFile("첨부 파일");
        noticeResDto.setImage("첨부 이미지");
        noticeResDto.setPin(true);
        return noticeResDto;
    }
}