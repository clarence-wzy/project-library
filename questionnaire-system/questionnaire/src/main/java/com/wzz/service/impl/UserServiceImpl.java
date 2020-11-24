package com.wzz.service.impl;

import com.wzz.entity.Permission;
import com.wzz.entity.RolePermission;
import com.wzz.entity.User;
import com.wzz.mapper.UserMapper;
import com.wzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>Title: UserServiceImpl</p>
 * <p>Description: </p>
 *
 * @author lizihao
 * @version 1.0.0
 * @date 2019/8/26 16:52
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUserInfoByName(String username) {
        return userMapper.getUserInfoByName(username);
    }

    @Override
    public RolePermission getPermIdByRoleId(int role_id) {
        return userMapper.getPermIdByRoleId(role_id);
    }

    @Override
    public Permission getPermDetailByPermId(int perm_id) {
        return userMapper.getPermDetailByPermId(perm_id);
    }

    @Override
    public int updatePasswordWithEncryption(String username, String password) {
        return userMapper.updatePasswordWithEncryption(username, password);
    }

}
