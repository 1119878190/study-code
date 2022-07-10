package com.imooc.ecommerce.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.ecommerce.vo.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <h1>Kafka 消费者</h1>
 *
 * @注意：kafka确保每个partition只能同一个group中的一个consumer消费，如果想要重复消费，那么需要其他的组来消费。
 *
 * @Author: lafe
 * @DateTime: 2022/7/6 20:25
 **/
@Slf4j
@Component
public class KafkaConsumer {

    // 序列化 可以用别的序列化工具
    @Resource
    private ObjectMapper mapper;


    /**
     * <h2>监听 Kafka 消息并消费</h2>
     *
     * @param record
     * @throws Exception
     */
    @KafkaListener(topics = {"test-springboot"}, groupId = "test-group-1")
    public void listener01(ConsumerRecord<String, String> record) throws Exception {

        String key = record.key();
        String value = record.value();

        TestMessage kafkaMessage = mapper.readValue(value, TestMessage.class);
        log.info("in listener01 consume kafka message : [{}],[{}]", key, mapper.writeValueAsString(kafkaMessage));

    }

    /**
     * <h2>监听 kafka 消息并消费</h2>
     *
     * @param record
     * @throws Exception
     */
    @KafkaListener(topics = {"test-springboot"}, groupId = "test-group-2")
    public void listener02(ConsumerRecord<?, ?> record) throws Exception {

        Optional<?> _kafkaMessage = Optional.ofNullable(record.value());
        if (_kafkaMessage.isPresent()) {
            Object message = _kafkaMessage.get();
            TestMessage kafkaMessage = mapper.readValue(message.toString(), TestMessage.class);
            log.info("in listener02 consume kafka message : [{}]", mapper.writeValueAsString(kafkaMessage));
        }

    }

}
