package com.example.autoever_1st.survey.entities;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.common.entities.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member_survey")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSurvey extends TimeStamp {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @Column(columnDefinition = "TEXT")
    private String answer;
}
