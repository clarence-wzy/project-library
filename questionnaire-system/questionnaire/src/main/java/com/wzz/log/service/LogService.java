package com.wzz.log.service;

import com.wzz.log.entity.Log;

import java.util.List;

/**
 * <p>Title: LogService</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/8/27 20:32
 */
public interface LogService {

    /**
     * 添加日志
     */
    public void addLog(Log log);

    /**
     * 通过用户ID查询日志记录
     * @param userId
     * @return
     */
    List<Log> getLogsByUserId(Long userId);
}
