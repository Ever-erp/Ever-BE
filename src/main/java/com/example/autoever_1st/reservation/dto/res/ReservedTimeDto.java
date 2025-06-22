package com.example.autoever_1st.reservation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservedTimeDto {
    private List<Integer> reservedTimes;
}
