// ReservationController
package com.example.autoever_1st.example_domain.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.example_domain.dto.res.ReservationResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

//@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RequestMapping("/mock/reservation")
public class ReservationController {

    private ReservationResDto testReservation() {
        ReservationResDto dto = new ReservationResDto();
        dto.setStartTime(3);
        dto.setHeadCount(2);
        dto.setReservationDesc("회의실 예약");
        dto.setRoomNum(5);
        return dto;
    }

    @GetMapping
    public ApiResponse<ReservationResDto> mockReservationGet() {
        return ApiResponse.success(testReservation(), 200);
    }

    @PostMapping
    public ApiResponse<ReservationResDto> mockReservationPost(@RequestBody ReservationResDto request) {
        return ApiResponse.success(testReservation(), 201);
    }

    @PutMapping
    public ApiResponse<ReservationResDto> mockReservationPut(@RequestBody ReservationResDto request) {
        ReservationResDto updated = testReservation();
        updated.setReservationDesc("예약 내용 업데이트 완료");
        updated.setHeadCount(3);
        return ApiResponse.success(updated, 200);
    }

    @DeleteMapping
    public ApiResponse<ReservationResDto> mockReservationDelete() {
        return ApiResponse.success(null, 200);
    }
} 