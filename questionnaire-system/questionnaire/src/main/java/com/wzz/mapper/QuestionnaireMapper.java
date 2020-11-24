package com.wzz.mapper;

import com.wzz.entity.Questionnaire;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Leewebi-n
 * @date 2019/8/29
 */
@Repository
@Mapper
public interface QuestionnaireMapper {

    public List<Questionnaire> findQuestionnaireByUserId(Long userId);

    public int deleteQuestionnaire(Long qCode);

    Questionnaire findQuestionnaireFromTempByQCode(Long qCode);

    void insertQuestionnaireTitle(Questionnaire questionnaire);

    Questionnaire findQuestionnaireByQCode(Long qCode);
}
