package com.example.autoever_1st.survey.service.Impl;

import com.example.autoever_1st.common.exception.CustomStatus;
import com.example.autoever_1st.common.exception.exception_class.business.DataNotFoundException;
import com.example.autoever_1st.organization.entities.ClassEntity;
import com.example.autoever_1st.organization.repository.ClassEntityRepository;
import com.example.autoever_1st.survey.dto.req.SurveyCreateDto;
import com.example.autoever_1st.survey.entities.Survey;
import com.example.autoever_1st.survey.repository.SurveyRepository;
import com.example.autoever_1st.survey.service.SurveyService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SurveyServiceImpl implements SurveyService {

    private final ClassEntityRepository classEntityRepository;
    private final SurveyRepository surveyRepository;

    public SurveyServiceImpl(ClassEntityRepository classEntityRepository, SurveyRepository surveyRepository) {
        this.classEntityRepository = classEntityRepository;
        this.surveyRepository = surveyRepository;
    }

    @Override
    public void createSurvey(SurveyCreateDto surveyCreateDto) {
        ClassEntity classEntity = classEntityRepository.findByNameAndCohort(surveyCreateDto.getClassName(), surveyCreateDto.getCohort())
                .orElseThrow(() -> new DataNotFoundException("반 정보를 찾을 수 없습니다.", CustomStatus.NOT_HAVE_DATA));
        Survey survey = Survey.builder()
                .uuid(UUID.randomUUID().toString())
                .title(surveyCreateDto.getTitle())
                .classEntity(classEntity)
                .question(surveyCreateDto.getQuestion())
                .questionMeta(surveyCreateDto.getQuestionMeta())
                .postDate(surveyCreateDto.getPostDate())
                .build();
        surveyRepository.save(survey);
    }
}
