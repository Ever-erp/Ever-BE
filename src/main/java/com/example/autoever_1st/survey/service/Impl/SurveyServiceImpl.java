package com.example.autoever_1st.survey.service.Impl;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.example.autoever_1st.organization.entities.ClassEntity;
import com.example.autoever_1st.organization.repository.ClassEntityRepository;
import com.example.autoever_1st.survey.dto.req.SurveyCreateDto;
import com.example.autoever_1st.survey.dto.res.SurveyResDto;
import com.example.autoever_1st.survey.entities.Survey;
import com.example.autoever_1st.survey.repository.SurveyRepository;
import com.example.autoever_1st.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final ClassEntityRepository classEntityRepository;
    private final SurveyRepository surveyRepository;
    private final MemberRepository memberRepository;

    @Override
    public SurveyResDto getSurvey(String uuid) {
        Survey survey = surveyRepository.findByUuid(uuid)
                .orElseThrow(() -> new DataNotFoundException("해당 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));
        return SurveyResDto.toDto(survey);
    }

    @Override
    public Page<SurveyResDto> getSurveyPage(Pageable pageable, Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        member.getPosition().getRole();
        Page<Survey> page = surveyRepository.findAll(pageable);
        return page.map(SurveyResDto::toDto);
    }
}
