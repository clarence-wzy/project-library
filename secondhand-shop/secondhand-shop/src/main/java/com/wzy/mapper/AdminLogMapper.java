package com.wzy.mapper;

import com.wzy.entity.AdminLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Package: com.wzy.mapper
 * @Author: Clarence1
 * @Date: 2020/2/12 16:08
 */
@Mapper
public interface AdminLogMapper {

    /**
     * @decription 插入管理员日志
     * @param adminLog
     * @return
     */
    int insertAdminLog(AdminLog adminLog);

}
