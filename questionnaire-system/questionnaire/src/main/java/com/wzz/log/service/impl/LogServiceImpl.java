package com.wzz.log.service.impl;

import com.wzz.log.entity.Log;
import com.wzz.log.service.LogService;
import com.wzz.mapper.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: LogServiceImpl</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/8/27 20:45
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void addLog(Log log) {
        logMapper.insertLog(log);
    }

    @Override
    public List<Log> getLogsByUserId(Long userId) {
        return logMapper.getLogsByUserId(userId);
    }

}
