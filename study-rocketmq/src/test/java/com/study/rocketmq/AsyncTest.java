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
 * 异步发送示例  生产者不用同步等待broker的发送确认，只需要异步监听
 * @author lx @date 2023/09/08
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class AsyncTest {

    private final String NAME_SERVER = "172.30.6.157:9876";

    /**
     * 异步测试：生产者不用同步等待broker的发送确认
     */
    @Test
    public void asyncSendTest() throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("async-producer-group");
        defaultMQProducer.setNamesrvAddr(NAME_SERVER);
        defaultMQProducer.start();

        Message message = new Message("asyncTopic", "这是一条异步消息".getBytes());
        defaultMQProducer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }

            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送失败");
            }
        });

        System.out.println("我先执行");
        System.in.read();

    }
}
