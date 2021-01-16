package com.ripen.service.impl;

import com.ripen.dao.entity.BkInfo;
import com.ripen.dao.entity.BkRecord;
import com.ripen.dao.mapper.BkBasicMapper;
import com.ripen.dao.mapper.BkRecordMapper;
import com.ripen.service.BkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public int updateRecord(int type, BkRecord bkRecord) {
        int rt = -1;
        String serId = bkRecord.getSerId();
        if (serId != null) {
            switch (type) {
                case 1:
                    String lendTime = String.valueOf(bkRecord.getLendTime());
                    String expireTime = String.valueOf(bkRecord.getExpireTime());
                    if (lendTime != null && expireTime != null) {
                        bkRecordMapper.updateRecord(serId, lendTime, null, expireTime);
                        rt = 0;
                    }
                    break;
                case 2:
                    String returnTime = String.valueOf(bkRecord.getReturnTime());
                    if (returnTime != null) {
                        bkRecordMapper.updateRecord(serId, null, returnTime, null);
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
    public List<BkRecord> getRecord(int type, String bkId, String serId) {
        List<BkRecord> bkRecordList = bkRecordMapper.getRecord(bkId, serId);
        if (type == 1) {
            List<BkInfo> bkInfoList = null;
            for (BkRecord bkRecord : bkRecordList) {
                bkInfoList = bkBasicMapper.getInfo(bkRecord.getBkId(), null, null);
                if (bkInfoList.size() > 0) {
                    bkRecord.setBkInfo(bkInfoList.get(0));
                }
            }
        }
        return bkRecordList;
    }

}
