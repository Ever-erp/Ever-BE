package com.example.autoever_1st.reservation.service;

import com.example.autoever_1st.reservation.dto.req.ReservationReqDto;
import com.example.autoever_1st.reservation.dto.req.RoomTimeReqDto;
import com.example.autoever_1st.reservation.dto.res.ReservationStatusDto;
import com.example.autoever_1st.reservation.dto.res.ReservedTimeDto;
import org.springframework.security.core.Authentication;

public interface ReservationService {
    ReservationStatusDto getReservationStatus(Authentication authentication);
    ReservedTimeDto getReservedTimesForRoom(RoomTimeReqDto roomTimeReqDto);
    void makeReservation(ReservationReqDto reservationReqDto, Authentication authentication);
}
