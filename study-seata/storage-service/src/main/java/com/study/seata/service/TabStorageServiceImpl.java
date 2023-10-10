package com.study.seata.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.seata.dao.TabStorageDao;
import com.study.seata.entity.TabStorageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("tabStorageService")
public class TabStorageServiceImpl extends ServiceImpl<TabStorageDao, TabStorageEntity> implements TabStorageService {

    @Autowired
    private TabStorageDao tabStorageDao;

    @Override
    @Transactional
    public boolean updateUseNum(long productId , long used) {
        int a = 100/0;
        int index = tabStorageDao.updateUsed(productId, used);
        return index > 0;
    }

}