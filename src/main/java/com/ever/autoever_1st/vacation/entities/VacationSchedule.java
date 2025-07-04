package com.ever.autoever_1st.vacation.entities;

import com.ever.autoever_1st.auth.entities.Member;
import com.ever.autoever_1st.common.entities.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacationSchedule extends TimeStamp {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    private LocalDate vacationDate;     // 휴가 날짜
    private String vacationType;        // 휴가 종류
    private String vacationDesc;        // 휴가 사유

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
