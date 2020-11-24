package com.wzy.service.impl;

import com.wzy.entity.Admin;
import com.wzy.entity.Mail;
import com.wzy.entity.RolePermission;
import com.wzy.entity.User;
import com.wzy.mapper.UserMapper;
import com.wzy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Package: com.wzy.service.impl
 * @Author: Clarence1
 * @Date: 2019/11/26 20:28
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public List<RolePermission> getRolePermission(String roleName) {
        return userMapper.getRolePermission(roleName);
    }

    @Override
    public List<User> findUserByUsernameWithRole(String username) {
        return userMapper.getUserRole(username);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public List<Integer> findAllAdmin(String remark) {
        return userMapper.findAllAdmin(remark);
    }

    @Override
    public User findUserByUserId(int userId) {
        return userMapper.findUserByUserId(userId);
    }

    @Override
    public User findUserByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public int updateUserInfo(User user) {
        return userMapper.updateUserInfo(user);
    }

    @Override
    public int updateStudentInfo(int userId, int studentId, String studentPassword) {
        return userMapper.updateStudentInfo(userId, studentId, studentPassword);
    }

    @Override
    public int updateEmail(String email, int userId) {
        return userMapper.updateEmail(email, userId);
    }

    @Override
    public int updatePhone(String phone, int userId) {
        return userMapper.updatePhone(phone, userId);
    }

    @Override
    public int updateHeadImage(String username, String headImage) {
        return userMapper.updateHeadImage(username, headImage);
    }

    @Override
    public List<Integer> findUserByAdminId(int adminId) {
        return userMapper.findUserByAdminId(adminId);
    }

    @Override
    public int updateCredit(int userId, int credit) {
        return userMapper.updateCredit(userId, credit);
    }

    @Override
    public int updatePasswordWithEncryption(String username, String password) {
        return userMapper.updatePasswordWithEncryption(username, password);
    }

    @Override
    public List<User> findAllByAdminId(int adminId) {
        return userMapper.findAllByAdminId(adminId);
    }

    @Override
    public int updateUserStatusByUserId(int userId, String userStatus) {
        return userMapper.updateUserStatusByUserId(userId, userStatus);
    }

    @Override
    public List<Admin> findAmdinByAdminId(int adminId) {
        return userMapper.findAmdinByAdminId(adminId);
    }

    @Override
    public String sendScse(String url, HttpMethod method, MultiValueMap<String, String> params) {
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.getForEntity(url, String.class);
        return responseEntity.getBody();
    }

    @Override
    public void sendSimpleMail(Mail mail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(mail.getRecipient());
        simpleMailMessage.setSubject(mail.getTheme());
        simpleMailMessage.setText(mail.getContent());
        javaMailSender.send(simpleMailMessage);
    }

}
