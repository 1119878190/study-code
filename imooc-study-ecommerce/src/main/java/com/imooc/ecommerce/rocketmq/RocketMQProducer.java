package com.imooc.ecommerce.rocketmq;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * <h1>通过 RocketMQ 发送消息</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/6 22:01
 **/
@Slf4j
@Component
public class RocketMQProducer {


    /**
     * 类似 Kafka 中的 topic， 默认的读写队列都是4个
     */
    private static final String TOPIC = "imooc-study-rocketmq";


    private final RocketMQTemplate rocketMQTemplate;

    private RocketMQProducer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }


    /**
     * <h2>使用同步的方式发送消息 ，不指定 key </h2>
     *
     * @param value 消息值
     */
    public void sendMessageWithValue(String value) {

        // 随机选择一个 Topic 的 Message Queue 发送消息
        SendResult sendResult = rocketMQTemplate.syncSend(TOPIC, value);
        log.info("sendMessageWithValue result : [{}]", JSON.toJSONString(sendResult));

        SendResult sendResultOrderly = rocketMQTemplate.syncSendOrderly(TOPIC, value, "test-partition-key(用于分区类似Kafka)");
        log.info("sendMessageWithValue orderly result: [{}]", JSON.toJSONString(sendResultOrderly));

    }


    /**
     * <h2>使用异步方式发送消息，指定 key</h2>
     *
     * @param value 消息值
     * @param key   不是用于分区的hashKey，而是用于查询消息的key,类似于消息的id
     */
    public void sendMessageWithKey(String key, String value) {

        Message<String> message = MessageBuilder.withPayload(value)
                .setHeader(RocketMQHeaders.KEYS, key).build();

        // 异步发送消息，并设定回调
        rocketMQTemplate.asyncSend(TOPIC, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("sendMessageWithKey success result: [{}]", JSON.toJSONString(sendResult));
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("sendMessageWithKey success result: [{}],[{}]", throwable.getMessage(), throwable);
            }
        });
    }


    /**
     * <h2>使用同步的方式发送消息，带有 tag ，且发送的是 java Pojo </h2>
     *
     * @param tag   标签（需要在消费中配置 selectorExpression ）
     * @param value 消息值
     */
    public void sendMessageWithTag(String tag, String value) {

        TestMessage testMessage = JSON.parseObject(value, TestMessage.class);
        SendResult sendResult = rocketMQTemplate.syncSend(
                String.format("%s:%s", TOPIC, tag),
                testMessage
        );

        log.info("sendMessageWithTag result : [{}]", JSON.toJSONString(sendResult));
    }

    /**
     * <h2>使用同步的方式发送消息，带有 key 和 tag</h2>
     *
     * @param key   消息id
     * @param tag   标签
     * @param value 消息值
     */
    public void sendMessageWithAll(String key, String tag, String value) {
        Message<String> message = MessageBuilder.withPayload(value)
                .setHeader(RocketMQHeaders.KEYS, key).build();
        SendResult sendResult = rocketMQTemplate.syncSend(
                String.format("%s:%s", TOPIC, tag),
                message
        );
        log.info("sendMessageWithAll result : [{}]", JSON.toJSONString(sendResult));
    }

}
