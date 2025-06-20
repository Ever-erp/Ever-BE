package com.example.autoever_1st.domain.entities;

import com.example.autoever_1st.common.entities.TimeStamp;
import com.example.autoever_1st.domain.entities.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor
public class Reservation extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "room_num", nullable = false)
    private int roomNum;

    @Column(name = "start_time", nullable = false)
    private int startTime;

    private String reservationDesc;

    private int headCount;



}
