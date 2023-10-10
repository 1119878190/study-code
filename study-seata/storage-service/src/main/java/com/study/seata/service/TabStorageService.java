package com.study.seata.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.study.seata.entity.TabStorageEntity;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2023-08-24 23:05:21
 */
public interface TabStorageService extends IService<TabStorageEntity> {


    boolean updateUseNum(long productId , long used);

}

