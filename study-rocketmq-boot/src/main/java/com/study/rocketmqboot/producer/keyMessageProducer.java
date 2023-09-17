package com.study.rocketmqboot.producer;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 带有 key 的消息发送者
 *
 * @author zqy
 * @date 2023/09/13
 */
@Component
public class keyMessageProducer {


    @Resource
    private RocketMQTemplate rocketMQTemplate;

//    @PostConstruct
    public void producer() {
        // 发送带有key的消息
        String key = UUID.randomUUID().toString();
        Message<String> message = MessageBuilder.withPayload("这是一个带有key的消息").setHeader(RocketMQHeaders.KEYS, key).build();
        SendResult sendResult = rocketMQTemplate.syncSend("bootKeyTopic", message);
        System.out.println("发送消息成功" + key);
    }

}
