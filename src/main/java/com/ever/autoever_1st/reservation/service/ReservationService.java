package com.ever.autoever_1st.reservation.service;

import com.ever.autoever_1st.reservation.dto.req.ReservationReqDto;
import com.ever.autoever_1st.reservation.dto.req.RoomTimeReqDto;
import com.ever.autoever_1st.reservation.dto.res.ReservationStatusDto;
import com.ever.autoever_1st.reservation.dto.res.ReservedTimeDto;
import org.springframework.security.core.Authentication;

public interface ReservationService {
    ReservationStatusDto getReservationStatus(Authentication authentication);
    ReservedTimeDto getReservedTimesForRoom(RoomTimeReqDto roomTimeReqDto);
    void makeReservation(ReservationReqDto reservationReqDto, Authentication authentication);

    void cancelReservation(int roomNum, int startTime, Authentication authentication);
}
