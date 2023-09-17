package com.study.rocketmqboot.producer;


import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 重试消息发送者
 *
 * @author zqy
 * @date 2023/09/17
 */
@Component
public class RetryMessageProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    @PostConstruct
    public void producer() {
        rocketMQTemplate.asyncSend("boot-retry-topic", "这是一条重试消息", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("RetryMessageProducer成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("RetryMessageProducer失败");
            }
        });

    }
}
