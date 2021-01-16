package com.ripen.service.impl;

import com.ripen.dao.entity.SysAdmin;
import com.ripen.dao.mapper.SysAdminMapper;
import com.ripen.service.SysAdminService;
import com.ripen.util.Page;
import com.ripen.util.ThreeDes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 类
 *
 * <p> 功能详细描述
 *
 * @author wuziyi
 * @version 2021/01/10 15:33
 * @see
 * @since 2021/01/10
 */
@Service
public class SysAdminServiceImpl implements SysAdminService {

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Override
    public String addAdmin(SysAdmin sysAdmin) {
        String curId = String.format("%05d", sysAdminMapper.getMaxId() + 1);
        sysAdmin.setAdmId(curId);
        String pwd = sysAdmin.getPwd();
        pwd = ThreeDes.encryptThreeDESECB(pwd);
        sysAdmin.setPwd(pwd);
        sysAdmin.setCreateTime(LocalDateTime.now());
        sysAdmin.setUpdateTime(LocalDateTime.now());
        return sysAdminMapper.addAdmin(sysAdmin) == 1 ? curId : null;
    }

    @Override
    public List<SysAdmin> getAdminWithCondition(SysAdmin sysAdmin, Page page) {
        if (sysAdmin == null) {
            return null;
        }
        if (page == null) {
            page = new Page();
            page.setNum(0);
            page.setSize(0);
        }
        return sysAdminMapper.getAdminWithCondition(sysAdmin, page);
    }

    @Override
    public int updateAdmin(SysAdmin sysAdmin) {
        if (sysAdmin.getAdmId() == null) {
            return -1;
        }
        return sysAdminMapper.updateAdmin(sysAdmin);
    }

}
