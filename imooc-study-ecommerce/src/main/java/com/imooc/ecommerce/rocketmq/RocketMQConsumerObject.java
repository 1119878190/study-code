package com.imooc.ecommerce.rocketmq;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.imooc.ecommerce.vo.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <h1>第四个 RocketMQ 消费者 ， 指定消费具有 tag 的消息，且消费的是 jav Pojo</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/11 23:17
 **/
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "imooc-study-rocketmq",
        consumerGroup = "lafe-springboot-rocketmq-tag-object",
        selectorExpression = "lafe"  // 根据 tag 过滤
)
public class RocketMQConsumerObject implements RocketMQListener<TestMessage> {

    @Override
    public void onMessage(TestMessage message) {

        log.info("consume message in RocketMQConsumerObject : [{}]", JSON.toJSONString(message));
        // do something
    }
}
