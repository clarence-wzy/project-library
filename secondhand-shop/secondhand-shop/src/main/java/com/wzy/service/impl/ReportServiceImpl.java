package com.wzy.service.impl;

import com.wzy.entity.Report;
import com.wzy.mapper.ReportMapper;
import com.wzy.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package: com.wzy.service.impl
 * @Author: Clarence1
 * @Date: 2020/1/19 17:53
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportMapper reportMapper;

    @Override
    public int insertReport(Report report) {
        return reportMapper.insertReport(report);
    }

    @Override
    public List<Report> findReport(int id, List<Integer> userIds, int productId, String timeType, int begin, int end) {
        return reportMapper.findReport(id, userIds, productId, timeType, begin, end);
    }

    @Override
    public int updateReport(int id, String auditContent, int deductCredit, String reportStatus) {
        return reportMapper.updateReport(id, auditContent, deductCredit, reportStatus);
    }
}
