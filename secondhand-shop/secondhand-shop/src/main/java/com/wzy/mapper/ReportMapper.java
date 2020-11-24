package com.wzy.mapper;

import com.wzy.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Package: com.wzy.mapper
 * @Author: Clarence1
 * @Date: 2020/1/19 17:53
 */
@Mapper
public interface ReportMapper {

    /**
     * @decription 插入举报信息
     * @param report
     * @return
     */
    int insertReport(Report report);

    /**
     * @decription 查询举报信息
     * @param id
     * @param userIds
     * @param productId
     * @param timeType
     * @param begin
     * @param end
     * @return
     */
    List<Report> findReport(int id, List<Integer> userIds, int productId, String timeType, int begin, int end);

    /**
     * @decription 审核结果--更新举报信息
     * @param id
     * @param auditContent
     * @param deductCredit
     * @param reportStatus
     * @return
     */
    int updateReport(int id, String auditContent, int deductCredit, String reportStatus);

}
