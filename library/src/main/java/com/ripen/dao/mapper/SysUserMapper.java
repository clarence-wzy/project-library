package com.ripen.dao.mapper;

import com.ripen.dao.entity.SysUser;
import com.ripen.util.Page;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 用户映射
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:10
 * @since 2021/01/09
 */
@Mapper
public interface SysUserMapper {

    /**
     * 新增用户
     *
     * @param sysUser
     * @return
     */
    int addUser(SysUser sysUser);

    /**
     * 获取全部用户信息
     *
     * @param page
     * @return
     */
    List<SysUser> getAllUser(@Param("page") Page page);

    /**
     * 获取用户信息
     *
     * @param sysUser
     * @param page
     * @return
     */
    List<SysUser> getUserWithCondition(@Param("sysUser") SysUser sysUser, @Param("page") Page page);

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return
     */
    int updateUser(SysUser sysUser);

    /**
     * 获取最大ID
     *
     * @return
     */
    int getMaxId();

}
