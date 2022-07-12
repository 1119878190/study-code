package com.imooc.ecommerce.rocketmq;

import com.alibaba.fastjson.JSON;
import com.imooc.ecommerce.vo.TestMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <h1>第二个 RocketMQ 消费者 ，制定了消费带有 tag 的消息</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/11 23:01
 **/
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "imooc-study-rocketmq",
        consumerGroup = "lafe-springboot-rocket-tag-string",
        selectorExpression = "lafe-tag"   // 根据 tag 过滤
)
public class RocketMQConsumerTagString implements RocketMQListener<String> {


    @Override
    public void onMessage(String message) {

        TestMessage rocketMessage = JSON.parseObject(message, TestMessage.class);
        log.info("consume message in RocketMQConsumerTagString : [{}] ", JSON.toJSONString(rocketMessage));
    }
}
