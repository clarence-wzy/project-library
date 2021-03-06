package com.ripen.service;

import com.ripen.dao.entity.BkInfo;
import com.ripen.dao.entity.BkRecord;
import com.ripen.service.impl.BkRecordServiceImpl;

import java.time.LocalDateTime;
import java.util.*;

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
     * 新增书籍记录
     *
     * @param bkRecordList
     * @return
     */
    int addRecord (List<BkRecord> bkRecordList);

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
     * @param bkRecord 书籍记录
     * @return
     */
    List<BkRecord> getRecord (int type, BkRecord bkRecord);

    /**
     * 根据书籍信息id统计数量
     *
     * @return
     */
    LinkedList<BkInfo> countByBkId ();

    /**
     * 是否可借阅书籍
     *
     * @param account
     * @param expireTime
     * @return
     */
    Map<String, Object> isBorrowBook (String account, LocalDateTime expireTime);

    /**
     * 获取归还书籍记录id中未归还书籍的记录id
     *
     * @param account
     * @param rcdIdList
     * @param expireTime
     * @return
     */
    List<String> getNoReturnBook (String account, List<String> rcdIdList, LocalDateTime expireTime);

    /**
     * 获取未归还书籍数量以及滞纳金
     *
     * @param account
     * @return
     */
    Map<String, Object> getNoReturn (String account);

}
