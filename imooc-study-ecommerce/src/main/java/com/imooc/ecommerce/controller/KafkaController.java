package com.imooc.ecommerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.ecommerce.kafka.KafkaProducer;
import com.imooc.ecommerce.vo.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <h1>SpringBoot 集成 kafka 发送消息</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/6 20:49
 **/
@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaController {


    @Resource
    private ObjectMapper mapper;

    @Resource
    private KafkaProducer kafkaProducer;


    /**
     * <h2>发送 kafka 消息</h2>
     *
     * @param key  key用来kafka分区，可以不用传
     * @param topic
     * @throws Exception
     */
    @GetMapping("/send-message")
    public void sendMessage(@RequestParam(required = false) String key,
                            @RequestParam String topic) throws Exception {

        TestMessage testMessage = new TestMessage(1, "good good study");
        kafkaProducer.sendMessage(key, mapper.writeValueAsString(testMessage), topic);
    }


}
