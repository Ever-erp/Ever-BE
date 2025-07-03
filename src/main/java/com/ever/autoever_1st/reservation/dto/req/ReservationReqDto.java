package com.ever.autoever_1st.reservation.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationReqDto {
    private int roomNum;
    private int startTime;
    private String headCount;
    private String reservationDesc;
}
