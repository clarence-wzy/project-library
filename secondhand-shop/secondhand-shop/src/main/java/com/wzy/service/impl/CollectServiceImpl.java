package com.wzy.service.impl;

import com.wzy.entity.Collect;
import com.wzy.entity.CollectWithProduct;
import com.wzy.mapper.CollectMapper;
import com.wzy.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package: com.wzy.service.impl
 * @Author: Clarence1
 * @Date: 2019/12/19 17:03
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectMapper collectMapper;

    @Override
    public int insertCollect(Collect collect) {
        return collectMapper.insertCollect(collect);
    }

    @Override
    public List<CollectWithProduct> findAllColllectByUserId(int userId, int startIndex, int pageSize) {
        return collectMapper.findAllColllectByUserId(userId, startIndex, pageSize);
    }

    @Override
    public int countAllColllectByUserId(int userId) {
        return collectMapper.countAllColllectByUserId(userId);
    }

    @Override
    public List<Collect> findCollectByUserIdAndProductId(int userId, int productId) {
        return collectMapper.findCollectByUserIdAndProductId(userId, productId);
    }

    @Override
    public int updateCollectStatus(Collect collect) {
        return collectMapper.updateCollectStatus(collect);
    }
}
