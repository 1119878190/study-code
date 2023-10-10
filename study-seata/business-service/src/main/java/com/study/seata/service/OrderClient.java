package com.study.seata.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-service")
public interface OrderClient {


    @GetMapping("order/create")
    Boolean create(@RequestParam("userId") long userId , @RequestParam("productId") long productId);

}
