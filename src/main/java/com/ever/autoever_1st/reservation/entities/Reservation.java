package com.ever.autoever_1st.reservation.entities;

import com.ever.autoever_1st.auth.entities.Member;
import com.ever.autoever_1st.common.entities.TimeStamp;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor
public class Reservation extends TimeStamp {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "room_num", nullable = false)
    private int roomNum;

    @Column(name = "start_time", nullable = false)
    private int startTime;

    private String reservationDesc;

    private int headCount;

    private LocalDate reservationDate;

    @Builder
    public Reservation(Member member, int roomNum, int startTime, String reservationDesc, int headCount, LocalDate reservationDate) {
        this.member = member;
        this.roomNum = roomNum;
        this.startTime = startTime;
        this.reservationDesc = reservationDesc;
        this.headCount = headCount;
        this.reservationDate = reservationDate;
    }
}
