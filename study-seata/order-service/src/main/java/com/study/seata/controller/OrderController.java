package com.study.seata.controller;

import com.study.seata.service.TabOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private TabOrderService tabOrderService;

    @GetMapping("/order/create")
    public Boolean create(@RequestParam("userId") long userId , @RequestParam("productId") long productId){
        return tabOrderService.create(userId,productId);
    }
}
