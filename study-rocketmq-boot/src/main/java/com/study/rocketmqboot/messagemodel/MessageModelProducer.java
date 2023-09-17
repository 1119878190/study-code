package com.study.rocketmqboot.messagemodel;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 消息模式演示发送者
 *
 * @author zqy
 * @date 2023/09/14
 */
@Component
public class MessageModelProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    @PostConstruct
    public void producer() {
        for (int i = 0; i < 5; i++) {
            rocketMQTemplate.asyncSend("messageModel-topic", "这是第" + i + "条消息", new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {

                    System.out.println("消息发送成功");
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println("消息发送失败");
                }
            });
        }
    }
}
