package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class ReservationResDto {
    private int startTime;             // 예약할 (시작)시간대 (총 8타임, 1시간)
    private int headCount;             // 인원 수
    private String reservationDesc;    // 예약 상세
    private int roomNum;               // 방 번호
}