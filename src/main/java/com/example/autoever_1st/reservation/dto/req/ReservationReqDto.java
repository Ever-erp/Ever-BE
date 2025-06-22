package com.example.autoever_1st.reservation.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationReqDto {
    private int roomNum;
    private int startTime;
    private int headCount;
    private String reservationDesc;
}
