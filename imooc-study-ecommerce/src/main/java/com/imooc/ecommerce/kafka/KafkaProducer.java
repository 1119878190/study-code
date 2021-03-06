package com.imooc.ecommerce.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.TimeUnit;

/**
 * <h1>Kafka 生产者</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/5 22:24
 **/
@Slf4j
@Component
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    /**
     * <h2>发送 Kafka 消息</h2>
     *
     * @param key
     * @param topic
     * @param value
     * @Return void
     */
    public void sendMessage(String key, String value, String topic) {
        if (StringUtils.isBlank(value) || StringUtils.isBlank(topic)) {
            throw new IllegalArgumentException("value or topic is null or empty");
        }
        ListenableFuture<SendResult<String, String>> future = StringUtils.isBlank(key) ?
                kafkaTemplate.send(topic, value) : kafkaTemplate.send(topic, key, value);

        // 异步回调的方式获取通知
        future.addCallback(
                success -> {
                    assert null != success && null != success.getRecordMetadata();
                    // 发送到 kafka 的 topic
                    String _topic = success.getRecordMetadata().topic();
                    // 发送消息到的分区
                    int partition = success.getRecordMetadata().partition();
                    // 消息在分区内的偏移量 offset
                    long offset = success.getRecordMetadata().offset();

                    log.info("send kafka message success : [{}],[{}],[{}] ", _topic, partition, offset);

                }, failure -> {
                    log.error("send kafka message failure : [{}],[{}],[{}]", key, value, topic);
                }
        );

        // 同步等待方式获取通知
        try {
            // 5秒还没获取到结果抛出异常
            SendResult<String, String> sendResult = future.get(5, TimeUnit.SECONDS);

            // 发送到 kafka 的 topic
            String _topic = sendResult.getRecordMetadata().topic();
            // 发送消息到的分区
            int partition = sendResult.getRecordMetadata().partition();
            // 消息在分区内的偏移量 offset
            long offset = sendResult.getRecordMetadata().offset();

            log.info("send kafka message success : [{}],[{}],[{}] ", _topic, partition, offset);

        }catch (Exception e){
            log.error("send kafka message failure : [{}],[{}],[{}]", key, value, topic);
        }
    }

}
