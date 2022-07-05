package com.imooc.ecommerce.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1> 通过代码自定义 Kafka 配置</h1>
 *
 * @Description:可以通过yml配置文件配置，或通过代码配置
 */
@Configuration
public class KafkaConfig {

    /**
     * kafka 服务器地址
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    /**
     * <h2> Kafka Producer 生产者工厂类配置</h2>
     *
     * @Params: []
     * @Return org.springframework.kafka.core.ProducerFactory<java.lang.String, java.lang.String>
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {

        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(configs);

    }

    /**
     * <h2> Kafka Producer 客户端</h2>
     *
     * @Params: []
     * @Return org.springframework.kafka.core.KafkaTemplate<java.lang.String, java.lang.String>
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * <h2>Kafka Consumer 消费者工厂类配置 </h2>
     *
     * @Params: []
     * @Return org.springframework.kafka.core.ConsumerFactory<java.lang.String, java.lang.String>
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // 最多拉去50条记录
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * <h2>Kafka Consumer 监听器工厂类配置</h2>
     *
     * @Params: []
     * @Return org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory<java.lang.String, java.lang.String>
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        // 并发数就是一个消费者实例起几个线程
        factory.setConcurrency(3);
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
