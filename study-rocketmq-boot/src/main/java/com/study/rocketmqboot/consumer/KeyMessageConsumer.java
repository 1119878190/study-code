package com.study.rocketmqboot.consumer;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 带有 key 的消息消费者
 *
 * @author zqy
 * @date 2023/09/13
 */
@Component
@RocketMQMessageListener(consumerGroup = "boot-key-consumer-group",
        topic = "bootKeyTopic"
)
public class KeyMessageConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
        System.out.println("消息的key为" + message.getKeys());
        System.out.println("消费者收到消息" + new String(message.getBody()));
    }
}
