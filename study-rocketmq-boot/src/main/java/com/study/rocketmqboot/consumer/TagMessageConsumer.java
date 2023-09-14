package com.study.rocketmqboot.consumer;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 带有 tag消息 的消费者
 *
 * @author zqy
 * @date 2023/09/13
 */
@Component
@RocketMQMessageListener(consumerGroup = "boot-tag-consumer-group",
        topic = "bootTagTopic",
        selectorType = SelectorType.TAG,  // tag 过滤模式
        selectorExpression = "tagA || tagB" // 消费 tagA 和 tagB

)
public class TagMessageConsumer implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
        System.out.println("tag消费者收到消息" + new String(message.getBody()));
    }
}
