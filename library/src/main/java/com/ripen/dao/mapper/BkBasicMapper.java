package com.ripen.dao.mapper;

import com.ripen.dao.entity.BkDetail;
import com.ripen.dao.entity.BkInfo;
import com.ripen.dao.entity.BkType;
import com.ripen.dao.entity.BkTypeInfo;
import com.ripen.util.Page;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 书籍基础映射
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:10
 * @since 2021/01/09
 */
@Mapper
public interface BkBasicMapper {

    /**
     * 新增书籍类型
     *
     * @param bkType
     * @return
     */
    int addType (BkType bkType);

    /**
     * 新增书籍信息
     *
     * @param bkInfo
     * @return
     */
    int addInfo (BkInfo bkInfo);

    /**
     * 新增书籍详情
     *
     * @param bkDetail
     * @return
     */
    int addDetail (BkDetail bkDetail);

    /**
     * 批量新增书籍类型与信息关联
     *
     * @param tiList
     * @return
     */
    int batchAddTypeInfo (@Param("tiList") List<BkTypeInfo> tiList);

    /**
     * 获取书籍类型
     *
     * @param status
     * @return
     */
    List<BkType> getType (@Param("status") Integer status);

    /**
     * 获取书籍信息
     *
     * @param bkId
     * @param status
     * @param page
     * @return
     */
    List<BkInfo> getInfo (@Param("bkId") String bkId, @Param("status") Integer status, @Param("page") Page page);

    /**
     * 获取书籍详情
     *
     * @param bkId
     * @param serId
     * @param page
     * @return
     */
    List<BkDetail> getDetail (@Param("bkId") String bkId, @Param("serId") String serId, @Param("page") Page page);

    /**
     * 多条件获取书籍信息
     *
     * @param bkId
     * @param btId
     * @param page
     * @return
     */
    List<BkInfo> getInfoWithType (@Param("bkId") String bkId, @Param("btId") Integer btId, @Param("page") Page page);

    /**
     * 获取带信息的书籍详情
     *
     * @param serId
     * @param page
     * @return
     */
    List<BkDetail> getDetailWithInfo (@Param("serId") String serId, @Param("page") Page page);

}
