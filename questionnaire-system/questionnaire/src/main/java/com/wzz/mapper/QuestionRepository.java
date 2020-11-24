package com.wzz.mapper;

import com.wzz.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created on 2019/8/28 by Tinchi
 *
 * @Description:    问卷问题的数据库访问代码
 **/
@Repository
@Mapper
public interface QuestionRepository extends MongoRepository<Question, ObjectId> {
    /**
     * 符合JPA规范命名方式，则不需要在实现该方法也可用
     * 意在对满足条件的文档按照用户名称进行模糊查询
     */

    //获取qCode模板下所有问题信息
    List<Question> findByQCode(Long q_code);

    //获取qCode模板下qId的问题
    Question findByQCodeAndQId(Long q_code,int q_id);

    //删除qCode该模板下qId的问题
    int deleteByQCodeAndQId(Long q_code,int q_id);
}
