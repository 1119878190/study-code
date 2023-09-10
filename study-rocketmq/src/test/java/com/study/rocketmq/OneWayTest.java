package com.study.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 单项发送： 不需要mq的返回确认
 *
 * @author zqy
 * @date 2023/09/08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OneWayTest {

    private final String NAME_SERVER = "172.30.6.157:9876";

    /**
     *
     */
    @Test
    public void oneWayTest() throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("oneway-producer-group");
        defaultMQProducer.setNamesrvAddr(NAME_SERVER);
        defaultMQProducer.start();

        Message message = new Message("onewayTopic", "这是一条异步消息".getBytes());
        defaultMQProducer.sendOneway(message);
        System.out.println("成功");
        defaultMQProducer.shutdown();

    }
}
