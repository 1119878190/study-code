package com.study.seata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.seata.dao.TabOrderDao;
import com.study.seata.entity.TabOrderEntity;
import com.study.seata.service.TabOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("tabOrderService")
public class TabOrderServiceImpl extends ServiceImpl<TabOrderDao, TabOrderEntity> implements TabOrderService {

    @Autowired
    private TabOrderDao tabOrderDao;

    @Override
    public Boolean create(long userId , long productId){
        TabOrderEntity order = new TabOrderEntity();
        order.setCount(1);
        order.setMoney(BigDecimal.valueOf(88));
        order.setProductId(productId);
        order.setUserId(userId);
        order.setStatus(0);;
        int insert = tabOrderDao.insert(order);
        if (insert > 0){
            return true;
        }else {
            return false;
        }
    }

}