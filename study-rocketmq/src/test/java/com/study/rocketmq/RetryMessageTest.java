package com.study.rocketmq;

import com.study.rocketmq.contant.SystemConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
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
 * 消息重试：
 * 1.生产者重试
 * 2.消费者消费消息失败重试
 * <p>
 * <p>
 * 基于消费者重试有两种方案
 * // ===============================第一种方案：消息过了消息次数后，将消息放到死信队列中处理 ===================================
 * // ==========================(推荐)第二種方案：在原來的consumer获取消息的重试次数进行业务判断处理=============================
 * <p>
 * 推荐第二种方案，应为第一种方案需要重新再写一个consumer 消费
 *
 * @author zqy
 * @date 2023/09/12
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RetryMessageTest {

    @Test
    public void producer() throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("retry-producer-group");
        defaultMQProducer.setNamesrvAddr(SystemConstant.NAME_SERVER);
        defaultMQProducer.start();

        // 生产者发送消息 重试次数
        defaultMQProducer.setRetryTimesWhenSendFailed(2);
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(2);
        Message message = new Message("retryTopic", "这是一消息".getBytes());
        defaultMQProducer.send(message);
        System.out.println("发送时间" + new Date());
        defaultMQProducer.shutdown();

    }


    @Test
    public void consumerA() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("retry-consumer-group");
        defaultMQPushConsumer.setNamesrvAddr(SystemConstant.NAME_SERVER);
        defaultMQPushConsumer.subscribe("retryTopic", "");
        // MessageListenerConcurrently 并发模式 多线程的  重试16次，可设定重试次数
        // MessageListenerOrderly 顺序模式 单线程的 无线重试
        // 设定重试次数  重试时间间隔 也是根据消息的延迟时间等级 冲10秒开始
        defaultMQPushConsumer.setMaxReconsumeTimes(2);
        defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt messageExt = list.get(0);
                int reconsumeTimes = messageExt.getReconsumeTimes();
                System.out.println("收到消息" + new String(messageExt.getBody()));
                System.out.println("重试次数" + reconsumeTimes);
                try {
                    // 业务处理  模拟失败
                    int i = 1 / 0;
                } catch (Exception e) {
                    // 消息重回队列
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
        System.in.read();
    }


    // ===============================第一种方案：消息过了消息次数后，将消息放到死信队列中处理 ===================================

    /**
     * 死信队列：用于接受消费失败的消息和过期没有消费的消息
     *
     * @throws Exception
     */
    @Test
    public void DeadConsumer() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("retry-dead-consumer-group");
        defaultMQPushConsumer.setNamesrvAddr(SystemConstant.NAME_SERVER);
        defaultMQPushConsumer.subscribe("%DLQ%retry-consumer-group", "");
        defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt messageExt = list.get(0);
                System.out.println("死信队列收到消息" + new String(messageExt.getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
        System.in.read();
    }

    // ==========================(推荐)第二種方案：在原來的consumer获取消息的重试次数进行业务判断处理=============================
    @Test
    public void consumerB() throws Exception {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("retry-consumer-group");
        defaultMQPushConsumer.setNamesrvAddr(SystemConstant.NAME_SERVER);
        defaultMQPushConsumer.subscribe("retryTopic", "");
        // MessageListenerConcurrently 并发模式 多线程的  重试16次，可设定重试次数
        // MessageListenerOrderly 顺序模式 单线程的 无线重试
        // 设定重试次数  重试时间间隔 也是根据消息的延迟时间等级 冲10秒开始
        defaultMQPushConsumer.setMaxReconsumeTimes(2);
        defaultMQPushConsumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                MessageExt messageExt = list.get(0);
                int reconsumeTimes = messageExt.getReconsumeTimes();
                System.out.println("收到消息" + new String(messageExt.getBody()));
                System.out.println("重试次数" + reconsumeTimes);
                try {
                    // 业务处理  模拟失败
                    int i = 1 / 0;
                } catch (Exception e) {
                    if (reconsumeTimes == 2) {
                        // 超过重试两次  直接记录问题 人工处理
                        System.out.println("消息到达重复消费次数 需要人工处理");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    // 未达到指定次数，重回队列消费
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
        System.in.read();
    }

}
