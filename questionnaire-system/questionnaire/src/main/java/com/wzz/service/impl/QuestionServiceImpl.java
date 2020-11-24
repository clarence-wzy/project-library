package com.wzz.service.impl;

import com.wzz.entity.Question;
import com.wzz.mapper.QuestionRepository;
import com.wzz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 2019/8/28 by Tinchi
 *
 * @Description: Question的具体实现类
 **/
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionMapper;

    @Autowired
    MongoTemplate mongoTemplate;

    //按照q_code找出该模板下的所有问题

    @Override
    public List<Question> findByQCode(Long qCode) {
        return questionMapper.findByQCode(qCode);
    }


    @Override
    public Question findByQCodeAndQId(Long qCode, int qId) {
        return questionMapper.findByQCodeAndQId(qCode, qId);
    }


    @Override
    public int deleteByQCodeAndQId(Long qCode, int qId) {
        return questionMapper.deleteByQCodeAndQId(qCode, qId);
    }


    @Transactional
    @Override
    public int insertQuestion(Question question) {
        questionMapper.save(question);
        return 1;
    }

    @Override
    public void deleteByQCode(Long qCode) {
        Query query = Query.query(Criteria.where("q_code").is(qCode));
        mongoTemplate.remove(query, Question.class);
    }

    @Override
    public int updateQCode(List<Question> list,int id) {

        for (Question question:list){
            question.setQCode(id);
        }
        questionMapper.saveAll(list);
        return 1;
    }

    @Override
    public int updateByQCodeAndQId(Long qCode, int qId, Question question) {

        Query query = new Query(Criteria.where("q_code").is(qCode).and("q_id").is(qId));
        Update update = Update.update("q_name",question.getQName());
        update.set("q_isRequired",question.getQ_isRequired())
                .set("q_type",question.getQ_type())
                .set("q_options",question.getQ_options())
                .set("isScore",question.getIsScore())
                .set("Score",question.getScore());

        if(question.getQName()!="" && question.getQName()!=null) {
            mongoTemplate.updateFirst(query, update, Question.class);
            return 1;
        }else {
            return 0;
        }
    }
}
