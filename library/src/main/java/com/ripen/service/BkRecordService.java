package com.ripen.service;

import com.ripen.dao.entity.BkRecord;
import com.ripen.service.impl.BkRecordServiceImpl;

import java.util.List;

/**
 * 书籍记录服务层
 *
 * @author Ripen.Y
 * @version 2021/01/16 23:06
 * @see BkRecordServiceImpl
 * @since 2021/01/16
 */
public interface BkRecordService {

    /**
     * 更新书籍记录
     *
     * @param type 类型，1：借出，2：归还
     * @param bkRecord 书籍记录
     * @return
     */
    int updateRecord (int type, BkRecord bkRecord);

    /**
     * 获取书籍记录
     *
     * @param type 类型，0：记录，1：记录 + 信息
     * @param bkId 书籍信息ID
     * @param serId 书籍编号
     * @return
     */
    List<BkRecord> getRecord (int type, String bkId, String serId);

}
