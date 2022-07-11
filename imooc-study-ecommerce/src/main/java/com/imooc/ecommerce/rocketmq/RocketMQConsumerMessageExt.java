package com.imooc.ecommerce.rocketmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <h1>第三个 RocketMQ 消费者 ,获取消息的 key</h1>
 *
 * @Author: lafe
 * @DateTime: 2022/7/11 23:08
 **/
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "imooc-study-rocketmq",
        consumerGroup = "lafe-springboot-rocketmq-message-ext"
)
public class RocketMQConsumerMessageExt implements RocketMQListener<MessageExt> {


    @Override
    public void onMessage(MessageExt message) {

        String value = new String(message.getBody());
        log.info("consume message in RocketMQConsumerMessageExt : [{}],[{}]", message.getKeys(), value);
        log.info("MessageExt : [{}]", JSON.toJSONString(message));  // 会慢一点
    }
}
