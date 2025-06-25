package com.example.autoever_1st.reservation.controller;

import com.example.autoever_1st.common.dto.response.ApiResponse;
import com.example.autoever_1st.reservation.dto.req.ReservationReqDto;
import com.example.autoever_1st.reservation.dto.req.RoomTimeReqDto;
import com.example.autoever_1st.reservation.dto.res.ReservationStatusDto;
import com.example.autoever_1st.reservation.dto.res.ReservedTimeDto;
import com.example.autoever_1st.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ReservationController {
    private final ReservationService reservationService;
    @GetMapping("/status")
    public ApiResponse<ReservationStatusDto> getReservationStatus(Authentication authentication) {
        ReservationStatusDto reservationStatusDto = reservationService.getReservationStatus(authentication);
        return ApiResponse.success(reservationStatusDto, HttpStatus.OK.value());
    }

    @PostMapping("/room/times")
    public ApiResponse<ReservedTimeDto> getReservedTimesForRoom(@RequestBody RoomTimeReqDto roomTimeReqDto) {
        ReservedTimeDto reservedTimeDto = reservationService.getReservedTimesForRoom(roomTimeReqDto);
        return ApiResponse.success(reservedTimeDto, HttpStatus.OK.value());
    }

    @PostMapping("/reserve")
    public ApiResponse<Void> makeReservation(@RequestBody ReservationReqDto reservationReqDto, Authentication authentication) {
        reservationService.makeReservation(reservationReqDto, authentication);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }

    @DeleteMapping("/reservation/cancel/{roomNum}/{startTime}")
    public ApiResponse<Void> cancelReservation(@PathVariable int roomNum, @PathVariable int startTime, Authentication authentication) {
        reservationService.cancelReservation(roomNum, startTime, authentication);
        return ApiResponse.success(null, HttpStatus.OK.value());
    }
}
