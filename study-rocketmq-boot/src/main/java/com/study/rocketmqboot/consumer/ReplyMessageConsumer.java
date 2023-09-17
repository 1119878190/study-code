package com.study.rocketmqboot.consumer;


import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

/**
 * 带有返回的消息接收者
 * <p>
 * RocketMQReplyListener
 * * @param <T> the type of data received by the listener
 * * @param <R> the type of data replying to producer
 *
 * @author zqy
 * @date 2023/09/17
 */
@Component
@RocketMQMessageListener(
        topic = "replyTopic",
        consumerGroup = "reply-consumer-group"
)
public class ReplyMessageConsumer implements RocketMQReplyListener<MessageExt, String> {

    @Override
    public String onMessage(MessageExt message) {

        System.out.println("ReplyMessageConsumer收到消息" + new String(message.getBody()));
        return "消费者收到消息";
    }
}
