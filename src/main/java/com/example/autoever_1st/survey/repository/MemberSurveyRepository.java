package com.example.autoever_1st.survey.repository;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.survey.entities.MemberSurvey;
import com.example.autoever_1st.survey.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberSurveyRepository extends JpaRepository<MemberSurvey, Long> {
    @Query("SELECT DISTINCT ms.survey.id FROM MemberSurvey ms WHERE ms.member.id = :memberId")
    List<Long> findDistinctSurveyIdsByMemberId(@Param("memberId") Long memberId);
    Optional<MemberSurvey> findBySurveyAndMember(Survey survey, Member member);
    boolean existsBySurveyAndMember(Survey survey, Member member);
    void deleteBySurvey(Survey survey);
    List<MemberSurvey> findBySurvey(Survey survey);
}
