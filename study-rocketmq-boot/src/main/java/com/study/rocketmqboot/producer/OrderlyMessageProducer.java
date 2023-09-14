package com.study.rocketmqboot.producer;

import com.alibaba.fastjson.JSONObject;
import com.study.rocketmqboot.entity.MsgModel;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 发送有序消息
 *
 * @author zqy
 * @date 2023/09/13
 */
@Component
public class OrderlyMessageProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

//    @PostConstruct
    public void producer() {
        //===================顺序消息====================
        // 这里我们要保证同一个人的  下单 短信 物流要有序
        List<MsgModel> orderData = Arrays.asList(
                new MsgModel("zhangsan", 1, "下单"),
                new MsgModel("zhangsan", 1, "短信"),
                new MsgModel("zhangsan", 1, "物流"),

                new MsgModel("lisi", 2, "下单"),
                new MsgModel("lisi", 2, "短信"),
                new MsgModel("lisi", 2, "物流")
        );
        for (MsgModel orderDatum : orderData) {
            // 我们根据 snId 进行hash操作，保证相同的 snId有序，发送到同一个queue中
            rocketMQTemplate.syncSendOrderly("boot-order-topic", JSONObject.toJSON(orderDatum), String.valueOf(orderDatum.getSnId()));
        }
    }
}
