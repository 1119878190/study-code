package com.study.rocketmq;

import com.study.rocketmq.contant.SystemConstant;
import com.study.rocketmq.domain.MsgModel;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * 带有tag的消息 ：给消息添加标签
 * <p>
 * 根据官网的 订阅关系一致性  https://rocketmq.apache.org/zh/docs/bestPractice/05subscribe
 * 同一个消费者组下所有消费者实例所订阅的Topic、Tag必须完全一致。如果订阅关系（消费者分组名-Topic-Tag）不一致，会导致消费消息紊乱，甚至消息丢失。
 * <p>
 * <p>
 * 在本例中，  consumerA的消费的tag未vip1 ，consumerB的消费的tag为vip1和vip2  ，故两个消费者不能在同一个消费组中
 *
 * @author zqy
 * @date 2023/09/12
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TagMessageTest {


    @Test
    public void producer() throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("tag-producer-group");
        defaultMQProducer.setNamesrvAddr(SystemConstant.NAME_SERVER);
        defaultMQProducer.start();

        Message message = new Message("tagTopic", "vip1", "这是vip1的消息".getBytes());
        defaultMQProducer.send(message);

        message = new Message("tagTopic", "vip2", "这是vip2的消息".getBytes());
        defaultMQProducer.send(message);

        System.out.println("发送完成" + new Date());
        defaultMQProducer.shutdown();
    }

    @Test
    public void consumerA() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("tag-consumerA-group");
        defaultMQPushConsumer.setNamesrvAddr(SystemConstant.NAME_SERVER);
        defaultMQPushConsumer.subscribe("tagTopic", "vip1");
        defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("我只vip1的消费者，我正在消费消息" + new String(list.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
        System.in.read();
    }


    @Test
    public void consumerB() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("tag-consumerB-group");
        defaultMQPushConsumer.setNamesrvAddr(SystemConstant.NAME_SERVER);
        defaultMQPushConsumer.subscribe("tagTopic", "vip1 || vip2");
        defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("我只vip1和vip2的消费者，我正在消费消息" + new String(list.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
        System.in.read();
    }
}
