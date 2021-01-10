package com.ripen.service;

import com.ripen.dao.entity.SysUser;
import com.ripen.service.impl.SysUserServiceImpl;
import com.ripen.util.Page;

import java.util.List;

/**
 * 系统用户服务层
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:39
 * @see SysUserServiceImpl
 * @since 2021/01/09
 */
public interface SysUserService {

    /**
     * 新增用户
     *
     * @param sysUser
     * @return
     */
    String addUser(SysUser sysUser);

    /**
     * 获取全部用户信息
     *
     * @param page
     * @return
     */
    List<SysUser> getAllUser(Page page);

    /**
     * 获取用户信息
     *
     * @param sysUser
     * @return
     */
    List<SysUser> getUserWithCondition(SysUser sysUser, Page page);

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return
     */
    int updateUser(SysUser sysUser);

}
