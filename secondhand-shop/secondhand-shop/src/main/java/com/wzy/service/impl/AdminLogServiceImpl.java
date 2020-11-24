package com.wzy.service.impl;

import com.wzy.entity.AdminLog;
import com.wzy.mapper.AdminLogMapper;
import com.wzy.service.AdminLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Package: com.wzy.service.impl
 * @Author: Clarence1
 * @Date: 2020/2/12 16:14
 */
@Service
public class AdminLogServiceImpl implements AdminLogService {

    @Autowired
    private AdminLogMapper adminLogMapper;

    @Override
    public int insertAdminLog(AdminLog adminLog) {
        return adminLogMapper.insertAdminLog(adminLog);
    }

}
