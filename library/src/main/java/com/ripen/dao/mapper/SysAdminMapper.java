package com.ripen.dao.mapper;

import com.ripen.dao.entity.SysAdmin;
import com.ripen.util.Page;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 管理员映射
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:10
 * @since 2021/01/09
 */
@Mapper
public interface SysAdminMapper {

    /**
     * 新增管理员
     *
     * @param sysAdmin
     * @return
     */
    int addAdmin(SysAdmin sysAdmin);

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

    /**
     * 获取最大ID
     *
     * @return
     */
    int getMaxId();


}
