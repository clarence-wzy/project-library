package com.wzz.service.impl;

import com.wzz.entity.Analyse;
import com.wzz.service.AnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2019/9/19
 *
 * @Author: tinchi
 * @Description:
 **/
@Service
public class AnalyseServiceImpl implements AnalyseService {

    @Autowired
    private MongoTemplate template;

    @Override
    public void insert(Analyse analyse) {
        template.insert(analyse);
    }

    @Override
    public List<Analyse> getAnalyse(Long qCode) {
        Criteria criteriaId=Criteria.where("qCode").is(qCode);
        Query queryId=Query.query(criteriaId);
        return template.find(queryId,Analyse.class);
    }
}
