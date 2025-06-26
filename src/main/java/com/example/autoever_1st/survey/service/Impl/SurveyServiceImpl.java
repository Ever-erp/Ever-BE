package com.example.autoever_1st.survey.service.Impl;

import com.example.autoever_1st.auth.entities.Member;
import com.example.autoever_1st.auth.repository.MemberRepository;
import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.example.autoever_1st.common.exception.exception_class.business.ValidationException;
import com.example.autoever_1st.survey.dto.SurveySubmitDto;
import com.example.autoever_1st.survey.dto.req.SurveyCreateDto;
import com.example.autoever_1st.survey.dto.req.SurveyUpdateDto;
import com.example.autoever_1st.survey.dto.res.MemberAnswerDto;
import com.example.autoever_1st.survey.dto.res.SurveyMemberResDto;
import com.example.autoever_1st.survey.dto.res.SurveyResDto;
import com.example.autoever_1st.survey.dto.res.SurveyWithMembersResDto;
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

    @Transactional
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

    @Transactional
    @Override
    public Page<SurveyResDto> getSurveyPage(String email, int page, int size) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        String role = member.getPosition().getRole();
        Pageable pageable = PageRequest.of(page, size, Sort.by("registedAt").descending());
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
    public void createSurvey(SurveyCreateDto surveyCreateDto, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        String role = member.getPosition().getRole();
        if (!"관리자".equals(role)) {
            throw new ValidationException("권한이 없습니다.", CustomStatus.INVALID_INPUT);
        }
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

    @Override
    @Transactional
    public void submitSurvey(String email, String surveyId, SurveySubmitDto surveySubmitDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        String role = member.getPosition().getRole();
        if (!"학생".equals(role)) {
            throw new ValidationException("권한이 없습니다.", CustomStatus.INVALID_INPUT);
        }
        Survey survey = surveyRepository.findByUuid(surveyId)
                .orElseThrow(() -> new DataNotFoundException("해당 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));
        if (survey.getDueDate().isBefore(LocalDate.now())) {
            throw new ValidationException("이미 마감된 설문은 제출할 수 없습니다.", CustomStatus.INVALID_INPUT);
        }
        if (memberSurveyRepository.existsBySurveyAndMember(survey, member)) {
            throw new ValidationException("이미 응답한 설문입니다.", CustomStatus.INVALID_INPUT);
        }
        String answerStr;
        try {
            answerStr = objectMapper.writeValueAsString(surveySubmitDto.getAnswerList());
        } catch (JsonProcessingException e) {
            throw new ValidationException("응답을 문자열로 변환하는 데 실패했습니다.", CustomStatus.INVALID_INPUT);
        }
        MemberSurvey memberSurvey = MemberSurvey.builder()
                .member(member)
                .survey(survey)
                .answer(answerStr)
                .build();

        memberSurveyRepository.save(memberSurvey);
    }

    @Override
    @Transactional
    public void updateSurvey(String email, String surveyId, SurveyUpdateDto surveyUpdateDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));

        String role = member.getPosition().getRole();
        if (!"관리자".equals(role)) {
            throw new ValidationException("권한이 없습니다.", CustomStatus.INVALID_INPUT);
        }
        if (surveyUpdateDto.getDueDate().isBefore(LocalDate.now())) {
            throw new ValidationException("이미 마감된 설문은 수정할 수 없습니다.", CustomStatus.INVALID_INPUT);
        }
        Survey survey = surveyRepository.findByUuid(surveyId)
                .orElseThrow(() -> new DataNotFoundException("해당 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));

        String questionStr;
        String metaStr;
        try {
            questionStr = objectMapper.writeValueAsString(surveyUpdateDto.getSurveyQuestion());
            metaStr = objectMapper.writeValueAsString(surveyUpdateDto.getSurveyQuestionMeta());
        } catch (JsonProcessingException e) {
            throw new ValidationException("질문을 문자열로 변환하는 데 실패했습니다.", CustomStatus.INVALID_INPUT);
        }
        // 필드 업데이트
        survey.updateSurvey(
                surveyUpdateDto.getSurveyTitle(),
                surveyUpdateDto.getSurveyDesc(),
                surveyUpdateDto.getDueDate(),
                surveyUpdateDto.getStatus(),
                surveyUpdateDto.getSurveySize(),
                questionStr,
                metaStr
        );
    }

    @Transactional
    @Override
    public void updateSurveyAnswer(String email, String surveyId, SurveySubmitDto surveySubmitDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        Survey survey = surveyRepository.findByUuid(surveyId)
                .orElseThrow(() -> new DataNotFoundException("해당 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));
        if (survey.getDueDate().isBefore(LocalDate.now())) {
            throw new ValidationException("이미 마감된 설문은 수정할 수 없습니다.", CustomStatus.INVALID_INPUT);
        }

        MemberSurvey memberSurvey = memberSurveyRepository.findBySurveyAndMember(survey, member)
                .orElseThrow(() -> new DataNotFoundException("해당 설문에 대한 응답이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));

        String answerStr;
        try {
            answerStr = objectMapper.writeValueAsString(surveySubmitDto.getAnswerList());
        } catch (JsonProcessingException e) {
            throw new ValidationException("응답을 문자열로 변환하는 데 실패했습니다.", CustomStatus.INVALID_INPUT);
        }

        memberSurvey.setAnswer(answerStr);
    }

    @Transactional
    @Override
    public void deleteSurvey(String email, String surveyId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));

        String role = member.getPosition().getRole();
        if (!"관리자".equals(role)) {
            throw new ValidationException("권한이 없습니다.", CustomStatus.INVALID_INPUT);
        }

        Survey survey = surveyRepository.findByUuid(surveyId)
                .orElseThrow(() -> new DataNotFoundException("해당 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));
        // 응답 먼저 삭제
        memberSurveyRepository.deleteBySurvey(survey);
        // 설문 삭제
        surveyRepository.delete(survey);
    }

    @Override
    @Transactional
    public void deleteSurveys(List<String> surveyIds, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));

        String role = member.getPosition().getRole();
        if (!"관리자".equals(role)) {
            throw new ValidationException("권한이 없습니다.", CustomStatus.INVALID_INPUT);
        }

        for (String surveyId : surveyIds) {
            Survey survey = surveyRepository.findByUuid(surveyId)
                    .orElseThrow(() -> new DataNotFoundException("설문을 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
            // 응답 먼저 삭제
            memberSurveyRepository.deleteBySurvey(survey);
            surveyRepository.delete(survey);
        }
    }

    @Transactional
    @Override
    public SurveyMemberResDto getSurveyWithMember(String surveyId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        String role = member.getPosition().getRole();
        if (!"학생".equals(role)) {
            throw new ValidationException("권한이 없습니다.", CustomStatus.INVALID_INPUT);
        }
        Survey survey = surveyRepository.findByUuid(surveyId)
                .orElseThrow(() -> new DataNotFoundException("해당 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));
        MemberSurvey memberSurvey = memberSurveyRepository.findBySurveyAndMember(survey, member)
                .orElseThrow(() -> new DataNotFoundException("응답 정보가 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));

        SurveyResDto surveyDto = SurveyResDto.toDto(survey);
        List<String> answers = memberSurvey.getAnswerList();

        return SurveyMemberResDto.builder()
                .survey(surveyDto)
                .member(MemberAnswerDto.builder()
                        .memberId(member.getId())
                        .memberName(member.getName())
                        .answer(answers)
                        .build())
                .build();
    }

    @Override
    @Transactional
    public SurveyWithMembersResDto getSurveyWithMembers(String surveyId, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("회원 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        String role = member.getPosition().getRole();
        if (!"관리자".equals(role)) {
            throw new ValidationException("권한이 없습니다.", CustomStatus.INVALID_INPUT);
        }
        Survey survey = surveyRepository.findByUuid(surveyId)
                .orElseThrow(() -> new DataNotFoundException("해당 설문이 존재하지 않습니다.", CustomStatus.NOT_HAVE_DATA));

        List<MemberSurvey> memberSurveys = memberSurveyRepository.findBySurvey(survey);

        List<MemberAnswerDto> memberInfos = memberSurveys.stream()
                .map(ms -> MemberAnswerDto.builder()
                        .memberId(ms.getMember().getId())
                        .memberName(ms.getMember().getName())
                        .answer(ms.getAnswerList())
                        .build())
                .toList();

        return SurveyWithMembersResDto.builder()
                .survey(SurveyResDto.toDto(survey))
                .members(memberInfos)
                .build();
    }
}
