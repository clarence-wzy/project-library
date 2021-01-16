package com.ripen.dao.mapper;

import com.ripen.dao.entity.BkRecord;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 书籍记录映射
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:10
 * @since 2021/01/09
 */
@Mapper
public interface BkRecordMapper {

    /**
     * 批量新增书籍记录
     *
     * @param recordList
     * @return
     */
    int batchAddRecord (@Param("recordList") List<BkRecord> recordList);

    /**
     * 获取书籍记录
     *
     * @param bkId
     * @param serId
     * @return
     */
    List<BkRecord> getRecord (@Param("bkId") String bkId, @Param("serId") String serId);

    /**
     * 根据书籍编号更新书籍记录
     *
     * @param serId
     * @param lendTime
     * @param returnTime
     * @param expireTime
     * @return
     */
    int updateRecord (@Param("serId") String serId, @Param("lendTime") String lendTime, @Param("returnTime") String returnTime, @Param("expireTime") String expireTime);

}
