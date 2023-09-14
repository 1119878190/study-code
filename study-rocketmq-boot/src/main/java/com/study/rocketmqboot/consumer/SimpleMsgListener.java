package com.study.rocketmqboot.consumer;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


@Component
@RocketMQMessageListener(consumerGroup = "boot-consumer-group",
        topic = "boot-topic")
public class SimpleMsgListener implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt msg) {
        System.out.println("消费者收到消息" + new String(msg.getBody()));
    }
}
