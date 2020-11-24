package com.wzy.service;

import com.wzy.entity.Report;

import java.util.List;

/**
 * @Package: com.wzy.service
 * @Author: Clarence1
 * @Date: 2020/1/19 17:53
 */
public interface ReportService {

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
         *
         * @param id
         * @param auditContent
         * @param deductCredit
         * @param reportStatus
         * @return
         */
    int updateReport(int id, String auditContent, int deductCredit, String reportStatus);

}
