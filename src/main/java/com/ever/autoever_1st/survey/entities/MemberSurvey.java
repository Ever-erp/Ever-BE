package com.ever.autoever_1st.survey.entities;

import com.ever.autoever_1st.common.entities.TimeStamp;
import com.ever.autoever_1st.common.exception.CustomStatus;
import com.ever.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.ever.autoever_1st.auth.entities.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    public List<String> getAnswerList() {
        try {
            return new ObjectMapper().readValue(this.answer, List.class);
        } catch (Exception e) {
            throw new ValidationException("파싱 실패", CustomStatus.INVALID_INPUT);
        }
    }
}
