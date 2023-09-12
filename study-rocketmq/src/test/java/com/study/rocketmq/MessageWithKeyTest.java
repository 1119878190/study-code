package com.study.rocketmq;

import com.study.rocketmq.contant.SystemConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * 带有唯一标识key的消息：用于解决消息重复消费的问题
 *
 * @author zqy
 * @date 2023/09/12
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageWithKeyTest {


    @Test
    public void producer() throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("key-producer-group");
        defaultMQProducer.setNamesrvAddr(SystemConstant.NAME_SERVER);
        defaultMQProducer.start();

        String key = UUID.randomUUID().toString();
        System.out.println("key:" + key);
        Message message = new Message("keyTopic", "vip1", key, "这是一条带有key唯一标识的消息".getBytes());
        defaultMQProducer.send(message);

        System.out.println("发送完成" + new Date());
        defaultMQProducer.shutdown();
    }

    @Test
    public void consumer() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("key-consumer-group");
        defaultMQPushConsumer.setNamesrvAddr(SystemConstant.NAME_SERVER);
        defaultMQPushConsumer.subscribe("keyTopic", "*");
        defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt messageExt = list.get(0);
                System.out.println("消费者消费消息" + new String(messageExt.getBody()) + "，key：" + messageExt.getKeys());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
        System.in.read();
    }

}
