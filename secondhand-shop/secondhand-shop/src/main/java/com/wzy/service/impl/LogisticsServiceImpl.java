package com.wzy.service.impl;

import com.wzy.entity.Logistics;
import com.wzy.mapper.LogisticsMapper;
import com.wzy.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package: com.wzy.service.impl
 * @Author: Clarence1
 * @Date: 2020/1/21 14:41
 */
@Service
public class LogisticsServiceImpl implements LogisticsService {

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Override
    public Logistics findLogistics(String logisticsId) {
        return logisticsMapper.findLogistics(logisticsId);
    }

    @Override
    public int insertLogistics(Logistics logistics) {
        return logisticsMapper.insertLogistics(logistics);
    }

    @Override
    public int updateLogistics(Logistics logistics) {
        return logisticsMapper.updateLogistics(logistics);
    }

    @Override
    public List<String> findLogisticsIdByIdOrType(String logisticsId, String logisticsType) {
        return logisticsMapper.findLogisticsIdByIdOrType(logisticsId, logisticsType);
    }
}
