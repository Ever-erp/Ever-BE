package com.example.autoever_1st.reservation.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ReservationStatusDto {
    private List<MyReservationInfo> myReservations;
    private List<Integer> fullyBookedRooms;

    public ReservationStatusDto(List<MyReservationInfo> myReservations, List<Integer> fullyBookedRooms) {
        this.myReservations = myReservations;
        this.fullyBookedRooms = fullyBookedRooms;
    }

    @Getter
    @NoArgsConstructor
    public static class MyReservationInfo {
        private int roomNum;
        private int startTime;

        public MyReservationInfo(int roomNum, int startTime) {
            this.roomNum = roomNum;
            this.startTime = startTime;
        }
    }
}
