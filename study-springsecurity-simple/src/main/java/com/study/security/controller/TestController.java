package com.study.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lx
 * @date 2023/10/11
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/query")
    public String test() {
        return "test";
    }

    @GetMapping("/everyOne")
    public String everyOne(){
        return "everyOne";
    }


}
