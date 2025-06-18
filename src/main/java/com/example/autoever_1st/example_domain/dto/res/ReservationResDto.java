package com.example.autoever_1st.example_domain.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class ReservationResDto {
    private int start_time;             // 예약할 (시작)시간대 (총 8타임, 1시간)
    private int head_count;             // 인원 수
    private String reservation_desc;    // 예약 상세
    private int room_num;               // 방 번호
}