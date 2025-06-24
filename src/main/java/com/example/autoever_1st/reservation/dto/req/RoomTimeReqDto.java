package com.example.autoever_1st.reservation.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RoomTimeReqDto {
    @NotNull(message = "방 번호는 필수입니다.")
    private int roomNum;
}
