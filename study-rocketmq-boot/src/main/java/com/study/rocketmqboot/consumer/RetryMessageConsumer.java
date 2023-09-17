package com.study.rocketmqboot.consumer;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * 重试消息发送者
 * 可以实现 RocketMQPushConsumerLifecycleListener  设置重试
 *
 * @author zqy
 * @date 2023/09/17
 */
@Component
@RocketMQMessageListener(
        topic = "boot-retry-topic",
        consumerGroup = "boot-retry-consumer-group"
)
public class RetryMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {


    @Override
    public void onMessage(MessageExt message) {
        System.out.println("第" + message.getReconsumeTimes() + "次收到消息");
        System.out.println("RetryMessageConsumer收到消息" + new String(message.getBody()));
        // 这里模拟消费失败
        throw new RuntimeException();
    }


    /**
     * implements RocketMQPushConsumerLifecycleListener
     * <p>
     * 这个方法可以对 consumer 设置一些属性
     *
     * @param consumer
     */
    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // 设定重试次数  重试的时间间隔也是根据消息的延迟时间等级 从10秒开始
        consumer.setMaxReconsumeTimes(2);
    }
}
