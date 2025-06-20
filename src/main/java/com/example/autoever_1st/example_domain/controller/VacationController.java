package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.example_domain.dto.res.VacationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

//@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RequestMapping("/mock/vacation")
public class VacationController {

    private VacationResDto testVacation() {
        VacationResDto dto = new VacationResDto();
        dto.setVacation_type("휴가");
        dto.setVacation_date(LocalDate.of(2025, 6, 18));
        dto.setVacation_desc("휴가 / 2025-06-18 ~ 2025-06-19 (2일)");
        return dto;
    }

    @GetMapping
    public ApiResponse<VacationResDto> mockVacationGet() {
        return ApiResponse.success(testVacation(), 200);
    }

    @PostMapping
    public ApiResponse<VacationResDto> mockVacationPost(@RequestBody VacationResDto request) {
        return ApiResponse.success(testVacation(), 201);
    }

    @PutMapping
    public ApiResponse<VacationResDto> mockVacationPut(@RequestBody VacationResDto request) {
        VacationResDto updated = testVacation();
        updated.setVacation_desc("업데이트된 휴가 사유입니다.");
        return ApiResponse.success(updated, 200);
    }

    @DeleteMapping
    public ApiResponse<VacationResDto> mockVacationDelete() {
        return ApiResponse.success(null, 200);
    }
}
