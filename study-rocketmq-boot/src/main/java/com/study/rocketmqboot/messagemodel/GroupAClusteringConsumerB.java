package com.study.rocketmqboot.messagemodel;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * 消息模式为集群：意味着同一个消费组内的消费者负载均衡消费消息，消息只能被一个消费者持有
 *
 * @author zqy
 * @date 2023/09/14
 */
@Component
@RocketMQMessageListener(
        consumerGroup = "messageModel-consumer-groupA",
        topic = "messageModel-topic",
//        messageModel = MessageModel.CLUSTERING  // 集群模式 负载均衡：消息只能被一个消费者持有
        messageModel = MessageModel.BROADCASTING  // 广播模式 消息会被每个消费者持有
)
public class GroupAClusteringConsumerB implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    @Override
    public void onMessage(MessageExt message) {
        System.out.println("我是groupA中的第二个消费者:" + new String(message.getBody()));
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        //设置当前实例的唯一名称,否则同组内多个消费者会报错
        consumer.setInstanceName("GroupAClusteringConsumerB");
    }
}
