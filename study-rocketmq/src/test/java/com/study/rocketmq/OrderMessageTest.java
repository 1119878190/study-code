package com.study.rocketmq;

import com.study.rocketmq.domain.MsgModel;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 顺序消息： 保证消息放到同一个queue中
 * @author zqy
 * @date 2023/09/11
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMessageTest {

    private final String NAME_SERVER = "172.30.6.157:9876";

    // 这里我们要保证同一个人的  下单 短信 物流要有序
    List<MsgModel> data = Arrays.asList(
      new MsgModel("zhangsan","1","下单"),
      new MsgModel("zhangsan","1","短信"),
      new MsgModel("zhangsan","1","物流"),

      new MsgModel("lisi","2","下单"),
      new MsgModel("lisi","2","短信"),
      new MsgModel("lisi","2","物流")

    );


    @Test
    public  void producer() throws Exception{
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("order-producer-group");
        defaultMQProducer.setNamesrvAddr(NAME_SERVER);
        defaultMQProducer.start();

        for (MsgModel msgModel : data) {
            Message message = new Message("orderTopic", msgModel.toString().getBytes());
            // 发相同订单号的消息到同一个queue中去 MessageQueueSelector
            defaultMQProducer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object arg) {
                    // snId % size
                    int hashCode = arg.toString().hashCode();
                    int queueIndex = hashCode % list.size();
                    return list.get(queueIndex);

                }
            },msgModel.getSnId());
        }

        System.out.println("发送完成"+new Date());
        defaultMQProducer.shutdown();
    }

    @Test
    public void consumer() throws Exception{
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer("order-consumer-group");
        defaultMQPushConsumer.setNamesrvAddr(NAME_SERVER);
        defaultMQPushConsumer.subscribe("orderTopic","");
        // MessageListenerConcurrently 并发模式 多线程的  重试16此
        // MessageListenerOrderly 顺序模式 单线程的 无线重试
        defaultMQPushConsumer.setMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                byte[] body = list.get(0).getBody();
                System.out.println("线程id"+ Thread.currentThread().getId());
                System.out.println(new String(body));
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        defaultMQPushConsumer.start();
        System.in.read();
    }
}
