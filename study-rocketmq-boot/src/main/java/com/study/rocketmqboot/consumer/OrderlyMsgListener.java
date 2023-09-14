package com.study.rocketmqboot.consumer;


import com.alibaba.fastjson.JSON;
import com.study.rocketmqboot.entity.MsgModel;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * 顺序消费消费者
 */
@Component
@RocketMQMessageListener(consumerGroup = "orderly-consumer-group",
        topic = "boot-order-topic",
        consumeMode = ConsumeMode.ORDERLY // 顺序消费模式， 单线程
)
public class OrderlyMsgListener implements RocketMQListener<MessageExt> {


    @Override
    public void onMessage(MessageExt message) {
        System.out.println("消费者收到消息" + JSON.parseObject(new String(message.getBody()), MsgModel.class));
    }
}
