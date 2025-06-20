package com.example.autoever_1st.notice.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ReservationStatusDto {
    private boolean reserved;
    private List<Integer> roomNums;
}
