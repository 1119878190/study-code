package com.imooc.ecommerce.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.imooc.ecommerce.rocketmq.RocketMQProducer;
import com.imooc.ecommerce.vo.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <h1>SpringBoot 集成 RocketMQ</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/12 22:12
 **/
@RestController
@Slf4j
@RequestMapping("/rocket-mq")
public class RocketMQController {


    private static final TestMessage testMessage = new TestMessage(1, "lafe-study-rocketmq-in-springboot");

    @Resource
    private RocketMQProducer rocketMQProducer;


    /**
     * 通过发送消息
     */
    @GetMapping("/message-with-value")
    public void sendMessageWithValue() {
        rocketMQProducer.sendMessageWithValue(JSON.toJSONString(testMessage));
    }

    /**
     * 异步发送消息 并指定 key(类似消息id)
     */
    @GetMapping("/message-with-key")
    public void sendMessageWithKey() {
        rocketMQProducer.sendMessageWithKey("lafe-key", JSON.toJSONString(testMessage));
    }

    /**
     * 带有 taa
     * 在消费者中 通过 selectorExpression 配置消费指定的 tag
     */
    @GetMapping("/message-with-tag")
    public void sendMessageWithTag() {
        rocketMQProducer.sendMessageWithTag("lafe-tag", JSON.toJSONString(testMessage));
    }

    /**
     * 同步发送 带有 key , tag  的消息
     */
    @GetMapping("/message-with-all")
    public void sendMessageWithAll() {
        rocketMQProducer.sendMessageWithAll("lafe-key", "lafe-tag", JSON.toJSONString(testMessage));
    }

}
