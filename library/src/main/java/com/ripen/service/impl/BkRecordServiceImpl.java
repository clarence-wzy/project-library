package com.ripen.service.impl;

import com.ripen.dao.entity.BkInfo;
import com.ripen.dao.entity.BkRecord;
import com.ripen.dao.mapper.BkBasicMapper;
import com.ripen.dao.mapper.BkRecordMapper;
import com.ripen.service.BkRecordService;
import com.ripen.util.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 书籍记录服务层实现类
 *
 * @author Ripen.Y
 * @version 2021/01/16 23:23
 * @see BkRecordService
 * @since 2021/01/16
 */
@Service
public class BkRecordServiceImpl implements BkRecordService {

    @Autowired
    private BkRecordMapper bkRecordMapper;

    @Autowired
    private BkBasicMapper bkBasicMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int addRecord(List<BkRecord> bkRecordList) {
        String rcdId;
        List<String> serIdList = new ArrayList<>(bkRecordList.size());
        for (BkRecord bkRecord : bkRecordList) {
            if (bkRecord.getAccount() == null || bkRecord.getBkId() == null || bkRecord.getSerId() == null) {
                return -2;
            }
            rcdId = "r_" + SnowFlakeUtil.getId();
            bkRecord.setRcdId(rcdId);

            serIdList.add(bkRecord.getSerId());
        }
        int lendNum = bkBasicMapper.batchModifyDetail(1, serIdList);
        if (lendNum != bkRecordList.size()
                || bkRecordMapper.batchAddRecord(bkRecordList) == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
        return 0;
    }

    @Override
    public int updateRecord(int type, BkRecord bkRecord) {
        int rt = -1;
        String rcdId = bkRecord.getRcdId();
        if (rcdId != null) {
            switch (type) {
                case 1:
                    String lendTime = String.valueOf(bkRecord.getLendTime());
                    String expireTime = String.valueOf(bkRecord.getExpireTime());
                    if (lendTime != null && expireTime != null) {
                        bkRecordMapper.updateRecord(rcdId, lendTime, null, expireTime);
                        rt = 0;
                    }
                    break;
                case 2:
                    String returnTime = String.valueOf(bkRecord.getReturnTime());
                    if (returnTime != null) {
                        List<String> rcdIdList = new ArrayList<>(4);
                        rcdIdList.add(rcdId);
                        List<String> serIdList = bkRecordMapper.getSerId(rcdIdList);
                        bkBasicMapper.batchModifyDetail(0, serIdList);
                        bkRecordMapper.updateRecord(rcdId, null, returnTime, null);
                        rt = 0;
                    }
                    break;
                default:
                    break;
            }
        }
        return rt;
    }

    @Override
    public List<BkRecord> getRecord(int type, BkRecord bkRecord) {
        List<BkRecord> bkRecordList = bkRecordMapper.getRecord(bkRecord);
        if (type == 1) {
            List<BkInfo> bkInfoList = null;
            for (BkRecord record : bkRecordList) {
                bkInfoList = bkBasicMapper.getInfo(record.getBkId(), null, null);
                if (bkInfoList.size() > 0) {
                    record.setBkInfo(bkInfoList.get(0));
                }
            }
        }
        return bkRecordList;
    }

    @Override
    public LinkedList<BkInfo> countByBkId() {
        LinkedList<BkInfo> bkInfoList = new LinkedList<>();
        List<Map<String, Object>> countList = bkRecordMapper.countByBkId();
        BkInfo bkInfo;
        for (Map<String, Object> countMap : countList) {
            String bkId = (String) countMap.get("bk_id");
            int num = Integer.parseInt(countMap.get("num").toString());

            List<BkInfo> tempBkInfoList = bkBasicMapper.getInfo(bkId, null, null);
            if (tempBkInfoList != null && tempBkInfoList.size() == 1) {
                bkInfo = tempBkInfoList.get(0);
                bkInfo.setBLendNum(num);
                bkInfoList.add(bkInfo);
            }
        }
        return bkInfoList;
    }

}
