package com.ripen.service.impl;

import com.ripen.dao.entity.*;
import com.ripen.dao.mapper.BkBasicMapper;
import com.ripen.dao.mapper.BkRecordMapper;
import com.ripen.dao.mapper.SysAdminMapper;
import com.ripen.service.BkBasicService;
import com.ripen.util.Page;
import com.ripen.util.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 书籍基础服务层实现
 *
 * @author Ripen.Y
 * @version 2021/01/16 16:02
 * @see BkBasicService
 * @since 2021/01/16
 */
@Service
public class BkBasicServiceImpl implements BkBasicService {

    @Autowired
    private BkBasicMapper bkBasicMapper;

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Autowired
    private BkRecordMapper bkRecordMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int addBook(BkInfo bkInfo, String admId, int num) {
        if (bkInfo == null || admId == null || num == 0) {
            return -1;
        }
        List<BkType> bkTypeList = bkInfo.getBkTypeList();
        if (bkTypeList.size() <= 0) {
            return -1;
        }

        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setAdmId(admId);
        List<SysAdmin> sysAdminList = sysAdminMapper.getAdminWithCondition(sysAdmin, null);
        if (sysAdminList == null || sysAdminList.size() != 1) {
            return -1;
        }
        List<Integer> btIdList = new ArrayList<>(bkTypeList.size());
        for (BkType bt : bkTypeList) {
            btIdList.add(bt.getBtId());
        }
        if (btIdList.size() <= 0
                && bkBasicMapper.getType(null, null, btIdList).size() != bkTypeList.size()) {
            // 书籍类型id列表为空，且书籍类型列表与数据库中的不一致
            return -1;
        }
        String bkId = "b_" + SnowFlakeUtil.getId();
        // 新增书籍类型与信息关联
        List<BkTypeInfo> bkTypeInfoList = new ArrayList<>(bkTypeList.size());
        BkTypeInfo bkTypeInfo = null;
        for (Integer id : btIdList) {
            bkTypeInfo = new BkTypeInfo();
            bkTypeInfo.setBkId(bkId);
            bkTypeInfo.setBtId(id);
            bkTypeInfoList.add(bkTypeInfo);
        }
        if (bkBasicMapper.batchAddTypeInfo(bkTypeInfoList) == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
        // 新增书籍信息
        bkInfo.setBkId(bkId);
        bkInfo.setBkInfoStatus(0);
        bkInfo.setCreateTime(LocalDateTime.now());
        bkInfo.setUpdateTime(LocalDateTime.now());
        if (bkBasicMapper.addInfo(bkInfo) != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }

        List<BkDetail> bkDetailList = new ArrayList<>(num);
        BkDetail bkDetail = null;
        List<BkRecord> bkRecordList = new ArrayList<>(num);
        BkRecord bkRecord = null;
        for (int i = 0 ; i < num ; i++) {
            String serId = "s" + SnowFlakeUtil.getId();

            bkDetail = new BkDetail();
            bkDetail.setSerId(serId);
            bkDetail.setBkId(bkId);
            bkDetail.setBkDetailStatus(0);
            bkDetail.setCreateTime(LocalDateTime.now());
            bkDetail.setAdmId(admId);
            bkDetailList.add(bkDetail);

            bkRecord = new BkRecord();
            bkRecord.setBkId(bkId);
            bkRecord.setSerId(serId);
            bkRecord.setUpdateTime(LocalDateTime.now());
            bkRecordList.add(bkRecord);
        }
        // 新增书籍详情
        if (bkBasicMapper.batchAddDetail(bkDetailList) == 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
        // 新增书籍记录信息
//        if (bkRecordMapper.batchAddRecord(bkRecordList) == 0) {
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//            return -1;
//        }

        return 0;
    }

    @Override
    public int addBkType(BkType bkType) {
        if (bkType == null) {
            return -2;
        }
        int btId = bkBasicMapper.getMaxTypeId() + 1;
        bkType.setBtId(btId);
        bkType.setBtStatus(0);
        bkType.setCreateTime(LocalDateTime.now());
        bkType.setUpdateTime(LocalDateTime.now());
        if (bkBasicMapper.addType(bkType) != 1) {
            return -1;
        }
        return btId;
    }

    @Override
    public List<BkType> getBkType(String bkId, Integer btId, Integer status, int type) {
        List<BkType> bkTypeList = null;
        if (bkId == null) {
            bkTypeList = bkBasicMapper.getType(status, btId, null);
        } else {
            bkTypeList = bkBasicMapper.getTypeByBkId(bkId, status, btId);
        }
        if (type == 1) {
            for (BkType bkType : bkTypeList) {
                bkType.setInfoNum(bkBasicMapper.countTypeInfo(bkType.getBtId()));
            }
        }
        return bkTypeList;
    }

    @Override
    public List<BkInfo> getBkInfo(String bkId, Integer btId, String bkName, Integer status, int type, Page page) {
        List<BkInfo> bkInfoList;
        if (btId == null) {
            bkInfoList = bkBasicMapper.getInfo(bkId, bkName, status, page);
        } else {
            bkInfoList = bkBasicMapper.getInfoWithCondition(bkId, btId, bkName, status, page);
        }
        if (type == 1) {
            // 为了提高性能，如果分页信息为null，或分页数量为0，则不获取书籍数量和借出数量
//            if (page == null || page.getNum() == 0) {} else {
                for (BkInfo bkInfo : bkInfoList) {
                    int bNum = 0, bLendNum = 0;
                    for (Map<String, Object> numList : bkBasicMapper.countDetailByStatus(bkInfo.getBkId()))
                    {
                        int num = Integer.parseInt(numList.get("num").toString());
                        int tempStatus = Integer.parseInt(numList.get("status").toString());
                        bNum += num;
                        if (tempStatus == 1) {
                            bLendNum += num;
                        }
                    }
                    bkInfo.setBNum(bNum);
                    bkInfo.setBLendNum(bLendNum);
                }
//            }
        }
        return bkInfoList;
    }

    @Override
    public List<BkDetail> getBkDetail(String bkId, String serId, int type, Page page) {
        List<BkDetail> bkDetailList = null;
        switch (type) {
            case 0:
                bkDetailList = bkBasicMapper.getDetail(bkId, serId, page);
                break;
            case 1:
                bkDetailList = bkBasicMapper.getDetailWithInfo(bkId, serId, page);
                break;
            case 2:
                bkDetailList = bkBasicMapper.getDetail(bkId, serId, page);
                for (BkDetail bd : bkDetailList) {
                    bd.setBkTypeList(bkBasicMapper.getTypeByBkId(bd.getBkId(), null, null));
                }
                break;
            case 3:
                bkDetailList = bkBasicMapper.getDetailWithInfo(bkId, serId, page);
                for (BkDetail bd : bkDetailList) {
                    bd.setBkTypeList(bkBasicMapper.getTypeByBkId(bd.getBkId(), null, null));
                }
                break;
            default:
                break;
        }
        return bkDetailList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyInfo(String bkId, BkInfo bkInfo) {
        if (bkId == null || bkInfo == null) {
            return -2;
        }
        try {
            bkInfo.setBkId(bkId);
            bkBasicMapper.modifyInfo(bkInfo);
            return 0;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int modifyTypeInfo(String bkId, List<Integer> addTypeList, List<Integer> delTypeList) {
        if (bkId == null) {
            return -2;
        }
        try {
            if (addTypeList != null && addTypeList.size() > 0) {
                List<BkTypeInfo> bkTypeInfoList = new ArrayList<>(addTypeList.size());
                BkTypeInfo bkTypeInfo = new BkTypeInfo();
                bkTypeInfo.setBkId(bkId);
                for (Integer btId : addTypeList) {
                    bkTypeInfo.setBtId(btId);
                    bkTypeInfoList.add(bkTypeInfo);
                }
                bkBasicMapper.batchAddTypeInfo(bkTypeInfoList);
            }
            if (delTypeList != null && delTypeList.size() > 0) {
                bkBasicMapper.delBatchTypeInfo(bkId, delTypeList);
            }
            return 0;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
    }

    @Override
    public int modifyType(Integer btId, BkType bkType) {
        if (btId == null || bkType == null) {
            return -2;
        }
        try {
            bkType.setBtId(btId);
            bkBasicMapper.modifyType(bkType);
            return 0;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return -1;
        }
    }

}
