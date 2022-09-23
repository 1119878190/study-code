package com.ex.controller;

import com.ex.service.TransactionalLose;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <h1> 事务失效场景 controller</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/27 22:02
 **/
@RestController
@RequestMapping("/transactional-lose")
@Slf4j
public class TransactionalLoseController {


    @Resource
    private TransactionalLose transactionalLose;


    /**
     * 失效场景 抛出了非 RuntimeException
     *
     * @throws Exception
     */
    @GetMapping("/wrong-rollback-for")
    public void wrongRollbackFor() throws Exception {
        log.info("coming in call wrong rollback for");
        transactionalLose.wrongRollbackFor();
    }


    /***
     * 失效场景： 同一个类中的方法调用
     * @throws Exception
     */
    @GetMapping("/wrong-inner-call")
    public void wrongInnerCall() throws Exception {
        log.info("coming in call wrong inner call");
        transactionalLose.wrongInnerCall();
    }

}
