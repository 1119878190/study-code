package com.study.rocketmqboot.producer;

import org.apache.rocketmq.spring.core.RocketMQLocalRequestCallback;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 发送带有返回的消息
 * <p>
 * 消费者通过RocketMQReplyListener 消费且回复消息
 *
 * @author zqy
 * @date 2023/09/17
 */
@Component
public class ReplyMsgProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @PostConstruct
    public void producer() {
        // 写法一
//        Object reply = rocketMQTemplate.sendAndReceive("replyTopic", "这是一条带有返回的消息", String.class,50000);
//        System.out.println("生产者收到消费者发来的消息" + reply.toString());
        // 写法二：
        // rocketMQLocalRequestCallback – callback that will invoked when reply message received.
        rocketMQTemplate.sendAndReceive("replyTopic", "这是一条带有返回的消息", new RocketMQLocalRequestCallback<String>() {
            @Override
            public void onSuccess(String message) {
                System.out.println("生产者收到消费者发来的消息" + message);
            }

            @Override
            public void onException(Throwable e) {
                System.out.println("异常");
            }
        },50000);

    }

}
