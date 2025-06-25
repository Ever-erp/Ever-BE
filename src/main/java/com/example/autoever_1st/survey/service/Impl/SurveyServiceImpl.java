package com.example.autoever_1st.survey.service.Impl;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.example.autoever_1st.survey.dto.res.SurveyResDto;
import com.example.autoever_1st.survey.entities.Survey;
import com.example.autoever_1st.survey.repository.MemberSurveyRepository;
import com.example.autoever_1st.survey.repository.SurveyRepository;
import com.example.autoever_1st.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final MemberRepository memberRepository;
    private final MemberSurveyRepository memberSurveyRepository;

    @Override
    public SurveyResDto getSurvey(String uuid) {
        Survey survey = surveyRepository.findByUuid(uuid)
                .orElseThrow(() -> new DataNotFoundException("해당 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));
        return SurveyResDto.toDto(survey);
    }

    @Override
    public Page<SurveyResDto> getSurveyPageForAdmin(Pageable pageable) {
        Page<Survey> page = surveyRepository.findAll(pageable);
        return page.map(SurveyResDto::toDto);
    }

    @Override
    public Page<SurveyResDto> getSurveyPageForUser(Long memberId, Pageable pageable) {
        List<Long> answeredSurveyIds = memberSurveyRepository.findDistinctSurveyIdsByMemberId(memberId);
        if (answeredSurveyIds.isEmpty()) {
            throw new DataNotFoundException("응답한 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA);
        }
        Page<Survey> surveys = surveyRepository.findByIdIn(answeredSurveyIds, pageable);

        return surveys.map(SurveyResDto::toDto);
    }
}
