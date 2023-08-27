package com.study.seata.service;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TMService {

    @Autowired
    private OrderClient orderClient;
    @Autowired
    private StorageClient storageClient;

    @GlobalTransactional
    public String buy(long userId, long productId) {
        orderClient.create(userId, productId);
        storageClient.changeStorage(userId, 1);
        return "ok";
    }

}
