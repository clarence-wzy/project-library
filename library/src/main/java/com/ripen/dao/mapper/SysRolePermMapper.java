package com.ripen.dao.mapper;

import com.ripen.dao.entity.SysRolePerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色权限映射层
 *
 * @author Ripen.Y
 * @version 2021/01/17 15:00
 * @since 2021/01/17
 */
@Mapper
public interface SysRolePermMapper {

    /**
     * 根据用户账号获取角色与权限
     *
     * @param account
     * @param type  用户类型，0：用户，1：管理员
     * @return
     */
    List<SysRolePerm> getRolePerm (@Param("account") String account, @Param("type") int type);

    /**
     * 新增用户角色关联
     *
     * @param sysRolePermList
     * @return
     */
    int addUserRole (@Param("rolePermList") List<SysRolePerm> sysRolePermList);

}
