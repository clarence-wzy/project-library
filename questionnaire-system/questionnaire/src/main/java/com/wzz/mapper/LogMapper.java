package com.wzz.mapper;

import com.wzz.log.entity.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>Title: LogMapper</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/8/28 8:49
 */
@Repository
public interface LogMapper {

    /**
     * 保存日志记录
     * @param log
     */
    public void insertLog(Log log);

    /**
     * 获取日志列表前五条记录
     * @param userId
     * @return
     */
    List<Log> getLogsByUserId(Long userId);
}
