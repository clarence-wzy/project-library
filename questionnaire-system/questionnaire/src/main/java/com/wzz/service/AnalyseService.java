package com.wzz.service;

import com.wzz.entity.Analyse;

import java.util.List;

/**
 * Created on 2019/9/19
 *
 * @Author: tinchi
 * @Description:
 **/


public interface AnalyseService {

    /**
     * 插入文档
     * @param analyse
     */
    void insert(Analyse analyse);

    /**
     *  查找相关文档
     * @param qCode
     * @return  document
     */
    List<Analyse> getAnalyse(Long qCode);


}
