package com.wzz.service.impl;

import com.wzz.entity.Questionnaire;
import com.wzz.mapper.QuestionnaireMapper;
import com.wzz.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lewebi-n
 * @date 2019/8/29
 */
@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireMapper mapper;
    @Override
    public List<Questionnaire> findQuestionnaireByUserId(Long userId) {
        return mapper.findQuestionnaireByUserId(userId);
    }


    @Override
    public int deleteQuestionnaire(Long qCode) {
        return mapper.deleteQuestionnaire(qCode);
    }


    @Override
    public Questionnaire findQuestionnaireFromTempByQCode(Long qCode) {
        return mapper.findQuestionnaireFromTempByQCode(qCode);
    }

    @Override
    public void insertQuestionnaireTitle(Questionnaire questionnaire) {
        mapper.insertQuestionnaireTitle(questionnaire);
    }


    @Override
    public Questionnaire findQuestionnaireByQCode(Long qCode) {
       return mapper.findQuestionnaireByQCode(qCode);
    }
}
