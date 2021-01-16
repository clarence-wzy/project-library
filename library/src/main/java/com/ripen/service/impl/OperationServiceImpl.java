package com.ripen.service.impl;

import com.ripen.dao.entity.Operation;
import com.ripen.dao.mapper.OperationMapper;
import com.ripen.service.OperationService;
import com.ripen.util.Page;
import com.ripen.util.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 日志操作服务层实现类
 *
 * @author Ripen.Y
 * @version 2021/01/17 0:50
 * @see OperationService
 * @since 2021/01/17
 */
@Service
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationMapper operationMapper;

    @Override
    public int addOpera(Operation operation) {
        operation.setOpId("op_" + SnowFlakeUtil.getId());
        operation.setCreateTime(LocalDateTime.now());
        return operationMapper.addOpera(operation);
    }

    @Override
    public List<Operation> getOpera(String opId, Integer opType, String opDetail, Page page) {
        return operationMapper.getOpera(opId, opType, opDetail, page);
    }
}
