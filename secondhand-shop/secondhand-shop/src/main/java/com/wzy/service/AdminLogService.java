package com.wzy.service;

import com.wzy.entity.AdminLog;

/**
 * @Package: com.wzy.service
 * @Author: Clarence1
 * @Date: 2020/2/12 16:13
 */
public interface AdminLogService {

    /**
     * @decription 插入管理员日志
     * @param adminLog
     * @return
     */
    int insertAdminLog(AdminLog adminLog);

}
