package com.ripen.service;

import com.ripen.dao.entity.SysAdmin;
import com.ripen.service.impl.SysAdminServiceImpl;
import com.ripen.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统管理员服务层
 *
 * @author Ripen.Y
 * @version 2021/01/10 15:32
 * @see SysAdminServiceImpl
 * @since 2021/01/10
 */
public interface SysAdminService {

    /**
     * 新增管理员
     *
     * @param sysAdmin
     * @return
     */
    String addAdmin(SysAdmin sysAdmin);

    /**
     * 获取管理员信息
     *
     * @param sysAdmin
     * @param page
     * @return
     */
    List<SysAdmin> getAdminWithCondition(@Param("sysAdmin") SysAdmin sysAdmin, @Param("page") Page page);

    /**
     * 更新管理员信息
     *
     * @param sysAdmin
     * @return
     */
    int updateAdmin(SysAdmin sysAdmin);

}
