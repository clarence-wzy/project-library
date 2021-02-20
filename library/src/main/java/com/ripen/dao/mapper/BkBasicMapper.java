package com.ripen.dao.mapper;

import com.ripen.dao.entity.BkDetail;
import com.ripen.dao.entity.BkInfo;
import com.ripen.dao.entity.BkType;
import com.ripen.dao.entity.BkTypeInfo;
import com.ripen.util.Page;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

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
     * 批量新增书籍详情
     *
     * @param bkDetailList
     * @return
     */
    int batchAddDetail (@Param("bkDetailList") List<BkDetail> bkDetailList);

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
     * @param btId
     * @param btIdList
     * @return
     */
    List<BkType> getType (@Param("status") Integer status, @Param("btId") Integer btId, @Param("btIdList") List<Integer> btIdList);

    /**
     * 根据书籍信息ID获取书籍类型
     *
     * @param bkId
     * @return
     */
    BkType getTypeByBkId (@Param("bkId") String bkId);

    /**
     * 获取书籍信息
     *
     * @param bkId
     * @param bkName
     * @param status
     * @param page
     * @return
     */
    List<BkInfo> getInfo (@Param("bkId") String bkId, @Param("bkName") String bkName, @Param("status") Integer status, @Param("page") Page page);

    /**
     * 多条件获取书籍信息
     *
     * @param bkId
     * @param btId
     * @param bkName
     * @param status
     * @param page
     * @return
     */
    List<BkInfo> getInfoWithCondition (@Param("bkId") String bkId, @Param("btId") Integer btId, @Param("bkName") String bkName, @Param("status") Integer status, @Param("page") Page page);

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
     * 获取带信息的书籍详情
     *
     * @param bkId
     * @param serId
     * @param page
     * @return
     */
    List<BkDetail> getDetailWithInfo (@Param("bkId") String bkId, @Param("serId") String serId, @Param("page") Page page);

    /**
     * 获取书籍类型最大ID
     *
     * @return
     */
    int getMaxTypeId ();

    /**
     * 获取书籍类型与信息关联的数量
     *
     * @param btId
     * @return
     */
    int countTypeInfo(@Param("btId") Integer btId);

    /**
     * 统计书籍数量和书籍借出数量
     *
     * @param bkId
     * @return
     */
    List<Map<String, Object>> countDetailByStatus(@Param("bkId") String bkId);

    /**
     * 修改书籍信息
     *
     * @param bkInfo
     * @return
     */
    int modifyInfo(BkInfo bkInfo);

    /**
     * 批量删除书籍类型信息关联
     *
     * @param bkId 书籍信息ID
     * @param btIdList 删除书籍类型列表
     * @return
     */
    int delBatchTypeInfo(@Param("bkId") String bkId, @Param("btIdList") List<Integer> btIdList);

    /**
     * 修改书籍类型
     *
     * @param bkType 书籍类型
     * @return
     */
    int modifyType(BkType bkType);

    /**
     * 批量修改书籍详情的状态
     *
     * @param status 状态，0：正常，1：借出
     * @param serIdList 书籍编号列表
     * @return
     */
    int batchModifyDetail(@Param("status") Integer status, @Param("serIdList") List<String> serIdList);

}
