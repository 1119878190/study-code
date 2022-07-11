package com.imooc.ecommerce.rocketmq;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <h1>第一个 RocketMQ 消费者</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/11 22:55
 **/
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "imooc-study-rocketmq",
        consumerGroup = "lafe-springboot-rocketmq-string"
)
public class RocketConsumerString implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {

        TestMessage rocketMessage = JSON.parseObject(message, TestMessage.class);
        log.info("consume message in RocketConsumerString : [{}] ", JSON.toJSONString(rocketMessage));

    }
}
