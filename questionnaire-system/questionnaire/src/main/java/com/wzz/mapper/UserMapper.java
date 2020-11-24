package com.wzz.mapper;

import com.wzz.entity.Permission;
import com.wzz.entity.RolePermission;
import com.wzz.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>Title: UserMapper</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/8/26 15:55
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 通过用户名查询用户信息
     * @param username
     * @return user
     */
    public User getUserInfoByName(String username);

    /**
     * 根据角色id查询权限id
     * @param role_id
     * @return
     */
    public RolePermission getPermIdByRoleId(int role_id);

    /**
     * 根据角色id查询权限id
     * @param perm_id
     * @return
     */
    public Permission getPermDetailByPermId(int perm_id);

    /**
     * 更新加密后的密码
     * @param username
     * @param password
     * @return
     */
    public int updatePasswordWithEncryption(String username, String password);

}
