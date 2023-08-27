package com.study.seata.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.study.seata.entity.TabOrderEntity;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-08-24 23:06:07
 */
public interface TabOrderService extends IService<TabOrderEntity> {

    public Boolean create(long userId ,long productId);
}

