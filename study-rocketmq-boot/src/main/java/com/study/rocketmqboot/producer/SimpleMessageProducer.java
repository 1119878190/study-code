package com.study.rocketmqboot.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SimpleMessageProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;


//    @PostConstruct
    public void sendMsg() {

        // 发送同步消息  topic为boot-topic
        SendResult sendResult = rocketMQTemplate.syncSend("boot-topic", "我是一个简单的消息");
        System.out.println("消息发送状态:" + sendResult.getSendStatus());
        System.out.println("消息id" + sendResult.getMsgId());

        // 发送异步消息
        rocketMQTemplate.asyncSend("boot-topic", "这是一个异步消息", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("异步消息发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("异步消息发送失败");
            }
        });

        // 单项消息
        rocketMQTemplate.sendOneWay("boot-topic", "这是一条单项消息");

        // 延迟消息
        Message<String> delayMessage = MessageBuilder.withPayload("这是一条延迟消息").build();
        rocketMQTemplate.syncSend("boot-topic", delayMessage, 3000, 3);



    }


}
