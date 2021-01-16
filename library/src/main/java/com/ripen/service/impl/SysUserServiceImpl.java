package com.ripen.service.impl;

import com.ripen.dao.entity.SysUser;
import com.ripen.dao.mapper.SysUserMapper;
import com.ripen.service.SysUserService;
import com.ripen.util.Page;
import com.ripen.util.ThreeDes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统用户服务层实现类
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:39
 * @see SysUserService
 * @since 2021/01/09
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public String addUser(SysUser sysUser) {
        String curAccount = String.format("%05d", sysUserMapper.getMaxId() + 1);
        sysUser.setAccount(curAccount);
        String pwd = sysUser.getPwd();
        pwd = ThreeDes.encryptThreeDESECB(pwd);
        sysUser.setPwd(pwd);
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setUpdateTime(LocalDateTime.now());
        return sysUserMapper.addUser(sysUser) == 1 ? curAccount : null;
    }

    @Override
    public List<SysUser> getAllUser(Page page) {
        if (page == null) {
            page = new Page();
            page.setNum(0);
            page.setSize(0);
        }
        return sysUserMapper.getAllUser(page);
    }

    @Override
    public List<SysUser> getUserWithCondition(SysUser sysUser, Page page) {
        if (sysUser == null) {
            return null;
        }
        if (page == null) {
            page = new Page();
            page.setNum(0);
            page.setSize(0);
        }
        return sysUserMapper.getUserWithCondition(sysUser, page);
    }

    @Override
    public int updateUser(SysUser sysUser) {
        if (sysUser.getAccount() == null) {
            return -1;
        }
        return sysUserMapper.updateUser(sysUser);
    }
}
