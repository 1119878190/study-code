package com.study.rocketmqboot.producer;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 带有 tag 的消息发送者
 *
 * @author zqy
 * @date 2023/09/13
 */
@Component
public class TagMessageProducer {


    @Resource
    private RocketMQTemplate rocketMQTemplate;

//    @PostConstruct
    public void producer() {
        // 发送带有tag的消息  格式：  topic:tag
        SendResult sendResult = rocketMQTemplate.syncSend("bootTagTopic:tagA", "这是一个带有tag的消息");
        System.out.println("发送消息成功");
    }

}
