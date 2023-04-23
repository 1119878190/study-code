package com.study.nettyservertest;

import com.study.nettyservertest.server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author lx
 * @date 2023/04/14
 */
@SpringBootApplication
public class NettyServerTestApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(NettyServerTestApplication.class, args);
    }

    @PostConstruct
    public void init() throws Exception {
        // 启动 Netty 服务
        new NettyServer(8088).start();


        //new FixedLengthFrameClient().start();


        //new DelimiterBasedFrameClient().start();


        //new LengthFieldBasedFrameClient().start();
    }


}
