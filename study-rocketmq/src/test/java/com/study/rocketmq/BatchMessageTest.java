package com.study.rocketmq;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 批量消息发送
 * @author zqy
 * @date 2023/09/08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BatchMessageTest {


    private final String NAME_SERVER = "172.30.6.157:9876";


    @Test
    public void producer() throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("batch-producer-group");
        defaultMQProducer.setNamesrvAddr(NAME_SERVER);
        defaultMQProducer.start();

        List<Message> list = Arrays.asList(
                new Message("batchTopic", "消息A".getBytes()),
                new Message("batchTopic", "消息B".getBytes()),
                new Message("batchTopic", "消息C".getBytes())
                );


        defaultMQProducer.send(list);
        System.out.println("发送时间"+new Date());
        defaultMQProducer.shutdown();

    }



    @Test
    public void consumer() throws Exception{
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("batch-consumer-group");
        defaultMQPushConsumer.setNamesrvAddr(NAME_SERVER);
        defaultMQPushConsumer.subscribe("batchTopic","");
        defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("接收到消息"+new Date());
                System.out.println(new java.lang.String(list.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();

        System.in.read();


    }
}
