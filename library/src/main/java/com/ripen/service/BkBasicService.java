package com.ripen.service;

import com.ripen.dao.entity.BkDetail;
import com.ripen.dao.entity.BkInfo;
import com.ripen.dao.entity.BkType;
import com.ripen.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 书籍基础服务层
 *
 * @author Ripen.Y
 * @version 2021/01/16 16:00
 * @see com.ripen.service.impl.BkBasicServiceImpl
 * @since 2021/01/16
 */
public interface BkBasicService {

    /**
     * 新增书籍
     *
     * @param bkInfo 书籍信息
     * @param admId 操作员ID
     * @param num 书籍数量
     * @return int 0：成功，-1：失败
     */
    int addBook(BkInfo bkInfo, String admId, int num);

    /**
     * 新增书籍类型
     *
     * @param bkType 书籍类型
     * @return int -1：失败，其他：书籍类型ID
     */
    int addBkType(BkType bkType);

    /**
     * 获取书籍类型
     *
     * @param btId 书籍类型ID
     * @param status 书籍类型状态
     * @param type 是否获取类型对应的书籍信息数量，0：否，1：是
     * @return
     */
    List<BkType> getBkType(Integer btId, Integer status, int type);

    /**
     * 获取书籍信息
     *
     * @param bkId 书籍信息ID
     * @param btId 书籍类型ID
     * @param status 书籍信息状态
     * @param type 是否获取信息对应的书籍数量，0：否，1：是
     * @param page 分页信息
     * @return
     */
    List<BkInfo> getBkInfo(String bkId, Integer btId, Integer status, int type, Page page);

    /**
     * 获取书籍详情
     *
     * @param bkId 书籍信息ID
     * @param serId 书籍编号
     * @param type 类型，0：详情，1：详情 + 信息，2：详情 + 类型，3：详情 + 信息 + 类型
     * @param page 分页信息
     * @return
     */
    List<BkDetail> getBkDetail(String bkId, String serId, int type, Page page);

    /**
     * 修改书籍信息
     *
     * @param bkId 书籍信息ID
     * @param bkInfo 书籍信息
     * @return
     */
    int modifyInfo(String bkId, BkInfo bkInfo);

    /**
     * 修改书籍类型信息关联
     *
     * @param bkId 书籍信息ID
     * @param addTypeList 新增类型列表
     * @param delTypeList 删除类型列表
     * @return
     */
    int modifyTypeInfo(String bkId, List<Integer> addTypeList, List<Integer> delTypeList);

    /**
     * 修改书籍类型
     *
     * @param btId 类型ID
     * @param bkType 书籍类型
     * @return
     */
    int modifyType(Integer btId, BkType bkType);

}
