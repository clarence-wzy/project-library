package com.wzy.mapper;

import com.wzy.entity.Admin;
import com.wzy.entity.RolePermission;
import com.wzy.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Package: com.wzy.mapper
 * @Author: Clarence1
 * @Date: 2019/11/26 20:28
 */
@Mapper
public interface UserMapper {

    /**
     * @decription 根据角色名查询对应的权限信息
     * @param roleName
     * @return
     */
    List<RolePermission> getRolePermission(String roleName);

    /**
     * @decription 根据用户名查询（含角色信息）
     * @param username
     * @return
     */
    List<User> getUserRole(String username);

    /**
     * @decription 添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * @decription 查询全部可用的管理员id（可根据备注查询）
     * @param remark
     * @return
     */
    List<Integer> findAllAdmin(String remark);

    /**
     * @decription 根据用户id查询用户信息
     * @param userId
     * @return
     */
    User findUserByUserId(int userId);

    /**
     * @decription 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * @decription 更新用户信息
     * @param user
     * @return
     */
    int updateUserInfo(User user);

    /**
     * @decription 更新用户的学生信息
     * @param userId
     * @param studentId
     * @param studentPassword
     * @return
     */
    int updateStudentInfo(int userId, int studentId, String studentPassword);

    /**
     * @decription 更新用户邮箱绑定
     * @param email
     * @param userId
     * @return
     */
    int updateEmail(String email, int userId);

    /**
     * @decription 更新用户手机绑定
     * @param phone
     * @param userId
     * @return
     */
    int updatePhone(String phone, int userId);

    /**
     * @decription 更新用户头像
     * @param username
     * @param headImage
     * @return
     */
    int updateHeadImage(String username, String headImage);

    /**
     * @decription 扣除用户信用度
     * @param userId
     * @param credit
     * @return
     */
    int updateCredit(int userId, int credit);

    /**
     * @decription 密码加密
     * @param username
     * @param password
     * @return
     */
    int updatePasswordWithEncryption(String username, String password);

    /**
     * @decription 根据用户的管理id查询用户id
     * @param adminId
     * @return
     */
    List<Integer> findUserByAdminId(int adminId);

    /**
     * @decription 根据管理员id查询用户
     * @param adminId
     * @return
     */
    List<User> findAllByAdminId(int adminId);

    /**
     * @decription 根据用户id更新用户状态
     * @param userId
     * @param userStatus
     * @return
     */
    int updateUserStatusByUserId(int userId, String userStatus);

    /**
     * @decription 根据管理员id查询管理员信息
     * @param adminId
     * @return
     */
    List<Admin> findAmdinByAdminId(int adminId);

}
