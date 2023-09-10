package com.study.rocketmq;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 简单发送示例
 * @author lx
 * @date 2023/09/08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleTest {

    private final String NAME_SERVER = "172.30.6.157:9876";

    @Test
    public void testSend() throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");
        // 连接namesrv
        producer.setNamesrvAddr(NAME_SERVER);
        // 启动
        producer.start();
        // 创建一个消息
        Message message = new Message("testTopic", "我是一个简单的消息".getBytes());
        // 发送消息
        SendResult sendResult = producer.send(message);
        System.out.println(sendResult.getSendStatus());

        // 关闭生产者
        producer.shutdown();

    }


    @Test
    public void testConsume() throws Exception{
        // 创建一个消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-consumer-group");
        // 连接namesrv
        consumer.setNamesrvAddr(NAME_SERVER);
        // 订阅一个主题
        consumer.subscribe("testTopic","");
        // 设置一个监听器
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                // 这个就是消费的方法（业务处理）
                System.out.println("我是消费者");
                System.out.println(list.get(0).toString());
                System.out.println(new java.lang.String(list.get(0).getBody()));
                System.out.println("消费上下文："+consumeConcurrentlyContext);

                //返回值CONSUME_SUCCESS成功，消息回从mq出列
                // CONSUME_SUCCESS（报错/null） 失败 消息会重新回到队列 过一会重新投递出来   给当前消费者活其它消费者
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

            }
        });

        // 启动
        consumer.start();

        // 挂起方法
        System.in.read();
    }
}
