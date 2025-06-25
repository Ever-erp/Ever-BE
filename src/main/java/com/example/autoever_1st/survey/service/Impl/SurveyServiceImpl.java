package com.example.autoever_1st.survey.service.Impl;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.example.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.example.autoever_1st.survey.dto.req.SurveyCreateDto;
import com.example.autoever_1st.survey.dto.res.SurveyResDto;
import com.example.autoever_1st.survey.entities.MemberSurvey;
import com.example.autoever_1st.survey.entities.Survey;
import com.example.autoever_1st.survey.repository.MemberSurveyRepository;
import com.example.autoever_1st.survey.repository.SurveyRepository;
import com.example.autoever_1st.survey.service.SurveyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    private final SurveyRepository surveyRepository;
    private final MemberRepository memberRepository;
    private final MemberSurveyRepository memberSurveyRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SurveyResDto getSurvey(String uuid, Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        Survey survey = surveyRepository.findByUuid(uuid)
                .orElseThrow(() -> new DataNotFoundException("해당 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));

        String role = member.getPosition().getRole();
        SurveyResDto surveyResDto;

        if ("관리자".equals(role)) {
            surveyResDto = SurveyResDto.toDto(survey);
        } else {
            MemberSurvey memberSurvey = memberSurveyRepository.findBySurveyAndMember(survey, member)
                    .orElseThrow(() -> new DataNotFoundException("해당 설문에 대한 응답이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));
            surveyResDto = SurveyResDto.withAnswer(survey, memberSurvey.getAnswerList());
        }

        return surveyResDto;
    }

    @Override
    public Page<SurveyResDto> getSurveyPage(String email, int page, int size) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        String role = member.getPosition().getRole();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Survey> surveys;

        if ("관리자".equals(role)) {
            surveys = surveyRepository.findAll(pageable);
        } else {
            List<Long> answeredSurveyIds = memberSurveyRepository.findDistinctSurveyIdsByMemberId(member.getId());
            if (answeredSurveyIds.isEmpty()) {
                throw new DataNotFoundException("응답한 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA);
            }
            surveys = surveyRepository.findByIdIn(answeredSurveyIds, pageable);
        }

        return surveys.map(SurveyResDto::toDto);
    }

    @Override
    @Transactional
    public void createSurvey(SurveyCreateDto surveyCreateDto) {
        // uuid 유효성 체크 or 생성
        String uuid = surveyCreateDto.getSurveyId();
        if (uuid == null || uuid.isBlank()) {
            uuid = UUID.randomUUID().toString();
        }
        if (surveyCreateDto.getSurveyQuestion() == null || surveyCreateDto.getSurveyQuestion().isEmpty()) {
            throw new ValidationException("설문 질문은 하나 이상 필요합니다.", CustomStatus.INVALID_INPUT);
        }
        if (surveyCreateDto.getSurveyQuestionMeta() == null
                || surveyCreateDto.getSurveyQuestionMeta().size() != surveyCreateDto.getSurveyQuestion().size()) {
            throw new ValidationException("문항 수와 문항 메타 수가 일치하지 않습니다.", CustomStatus.INVALID_INPUT);
        }
        String questionStr;
        String metaStr;
        try {
            questionStr = objectMapper.writeValueAsString(surveyCreateDto.getSurveyQuestion());
            metaStr = objectMapper.writeValueAsString(surveyCreateDto.getSurveyQuestionMeta());
        } catch (JsonProcessingException e) {
            throw new ValidationException("질문을 문자열로 변환하는 데 실패했습니다.", CustomStatus.INVALID_INPUT);
        }

        Survey survey = Survey.builder()
                .uuid(uuid)
                .title(surveyCreateDto.getSurveyTitle())
                .description(surveyCreateDto.getSurveyDesc())
                .dueDate(surveyCreateDto.getDueDate())
                .status(surveyCreateDto.getStatus())
                .surveySize(surveyCreateDto.getSurveySize())
                .question(questionStr)
                .questionMeta(metaStr)
                .postDate(LocalDate.now())
                .build();

        surveyRepository.save(survey);
    }

//    @Override
//    public Page<SurveyResDto> getSurveyPageForAdmin(Pageable pageable) {
//        Page<Survey> page = surveyRepository.findAll(pageable);
//        return page.map(SurveyResDto::toDto);
//    }

//    @Override
//    public Page<SurveyResDto> getSurveyPageForUser(Long memberId, Pageable pageable) {
//        List<Long> answeredSurveyIds = memberSurveyRepository.findDistinctSurveyIdsByMemberId(memberId);
//        if (answeredSurveyIds.isEmpty()) {
//            throw new DataNotFoundException("응답한 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA);
//        }
//        Page<Survey> surveys = surveyRepository.findByIdIn(answeredSurveyIds, pageable);
//
//        return surveys.map(SurveyResDto::toDto);
//    }
}
