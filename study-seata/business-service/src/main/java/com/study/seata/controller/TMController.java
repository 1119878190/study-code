package com.study.seata.controller;

import com.study.seata.service.TMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TMController {

    @Autowired
    private TMService tmService;

    @GetMapping("/buy")
    public String buy(long userId, long productId) {
        tmService.buy(userId, productId);
        return "ok";
    }

}
