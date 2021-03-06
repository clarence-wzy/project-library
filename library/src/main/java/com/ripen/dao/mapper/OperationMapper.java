package com.ripen.dao.mapper;

import com.ripen.dao.entity.Operation;
import com.ripen.util.Page;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 操作日志映射
 *
 * @author Ripen.Y
 * @version 2021/01/09 22:10
 * @since 2021/01/09
 */
@Mapper
public interface OperationMapper {

    /**
     * 新增操作日志
     *
     * @param operation
     * @return
     */
    int addOpera (Operation operation);

    /**
     * 获取操作日志
     *
     * @param opId
     * @param opType
     * @param opDetail
     * @param page
     * @return
     */
    List<Operation> getOpera (@Param("opId") String opId, @Param("opType") Integer opType, @Param("opDetail") String opDetail, @Param("page") Page page);

}
