package com.ripen.dao.mapper;

import com.ripen.dao.entity.BkRecord;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

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
     * @param bkRecord
     * @return
     */
    List<BkRecord> getRecord (BkRecord bkRecord);

    /**
     * 根据书籍记录id更新书籍记录
     *
     * @param rcdId
     * @param lendTime
     * @param returnTime
     * @param expireTime
     * @return
     */
    int updateRecord (@Param("rcdId") String rcdId, @Param("lendTime") String lendTime, @Param("returnTime") String returnTime, @Param("expireTime") String expireTime);

    /**
     * 根据书籍信息id统计数量
     *
     * @return
     */
    List<Map<String, Object>> countByBkId ();

    /**
     * 获取书籍编号
     *
     * @param rcdIdList
     * @return
     */
    List<String> getSerId (@Param("rcdIdList") List<String> rcdIdList);

}
