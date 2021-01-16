package com.ripen.service;

import com.ripen.dao.entity.Operation;
import com.ripen.util.Page;

import java.util.List;

/**
 * 日志操作服务层
 *
 * @author Ripen.Y
 * @version 2021/01/17 0:49
 * @see
 * @since 2021/01/17
 */
public interface OperationService {

    /**
     * 新增操作日志
     *
     * @param operation
     * @return
     */
    int addOpera (Operation operation);

    /**
     * 获取操作日志
     *
     * @param opId
     * @param opType
     * @param opDetail
     * @param page
     * @return
     */
    List<Operation> getOpera (String opId, Integer opType, String opDetail, Page page);


}
