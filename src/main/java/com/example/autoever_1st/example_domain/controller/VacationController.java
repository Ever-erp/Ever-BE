package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.example_domain.dto.res.VacationResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j      // Log 메세지 출력을 위한 어노테이션
@RestController // REST API (GET, POST, PUT, DELETE)
//@RequiredArgsConstructor    // 생성자를 통한 의존성 주입
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:5173"})     // CORS 설정 : error 감지 방지
@RequestMapping("/mock/vacation")
public class VacationController {
    @GetMapping("/test")
    public VacationResDto mockRegister() {
        VacationResDto vacationResDto = new VacationResDto();
        vacationResDto.setVacation_type("휴가");
        vacationResDto.setVacation_date(LocalDate.of(2020,6,18));
        vacationResDto.setVacation_desc("휴가 / 2025-06-18 ~ 2025-06-19 (2일)");
        return vacationResDto;
    }
}
