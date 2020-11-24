package com.wzz.service;

import com.wzz.entity.Questionnaire;

import java.util.List;

public interface QuestionnaireService {

    List<Questionnaire> findQuestionnaireByUserId(Long userId);

    int deleteQuestionnaire(Long qCode);

    Questionnaire findQuestionnaireFromTempByQCode(Long qCode);

    void insertQuestionnaireTitle(Questionnaire questionnaire);

    Questionnaire findQuestionnaireByQCode(Long qCode);
}
