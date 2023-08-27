package com.study.seata.controller;

import com.study.seata.service.TabStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lx
 */
@RestController
public class StorageController {


    @Autowired
    private TabStorageService tabStorageService;

    @GetMapping("/storage/change")
    public Boolean changeStorage(@RequestParam("productId") long productId , @RequestParam("used")  int used)  {
        return tabStorageService.updateUseNum(productId , used);
    }
}
