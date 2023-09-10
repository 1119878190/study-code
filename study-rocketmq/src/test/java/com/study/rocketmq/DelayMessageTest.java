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

import java.util.Date;
import java.util.List;


/**
 * 延迟消息发送示例：消息会存放在mq中指定的时间才能被消费者消费
 * @author zqy
 * @date 2023/09/08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class DelayMessageTest {

    private final String NAME_SERVER = "172.30.6.157:9876";


    @Test
    public void delayMessageTest() throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("delay-producer-group");
        defaultMQProducer.setNamesrvAddr(NAME_SERVER);
        defaultMQProducer.start();

        Message message = new Message("delayTopic", "这是一延迟消息".getBytes());
        // 给消息设置一个延迟等级
        // messageDelayLevel = " 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h "
        message.setDelayTimeLevel(3);
        defaultMQProducer.send(message);
        System.out.println("发送时间"+new Date());
        defaultMQProducer.shutdown();

    }



    @Test
    public void consumer() throws Exception{
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("delay-consumer-group");
        defaultMQPushConsumer.setNamesrvAddr(NAME_SERVER);
        defaultMQPushConsumer.subscribe("delayTopic","");
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
