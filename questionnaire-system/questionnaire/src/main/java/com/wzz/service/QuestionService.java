package com.wzz.service;

import com.wzz.entity.Question;

import java.util.List;

/**
 * Created on 2019/8/28 by Tinchi
 *
 * @Description:
 **/
public interface QuestionService {


    /**
     *  从模板问题库获取相对应的整套问题
     * @param qCode
     * @return  List<Question>集合
     */
    List<Question> findByQCode(Long qCode);


    //按照q_code找出该模板下q_id为qId的问题
    Question findByQCodeAndQId(Long qCode,int qId);

    //删除q_code该模板下q_id为qId的问题
    int deleteByQCodeAndQId(Long qCode,int qId);

    //新增q_code该模板下q_id为qId的问题
    int insertQuestion(Question question);

    //根据qCode删除所有问题
    void deleteByQCode(Long qCode);

    //更新
//    int update(@RequestBody Question question);


    //将自定义库信息存入MySQL，并返回一个自增Id

    //将返回的自增id替换原有的q_code,并存入自定义问题库
    int updateQCode (List<Question> list,int id);

    //更新单个问题
    int updateByQCodeAndQId(Long qCode, int qId, Question question);
}
