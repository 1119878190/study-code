package com.study.nettyclienttwo.listener;

import com.study.nettyclienttwo.service.SocketClientTwo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NettyClientStartListener implements ApplicationRunner {
    @Resource
    private SocketClientTwo socketClientTwo;
    /**
     * netty服务监听端口
     */
    @Value("${netty.port:8088}")
    private int port;

    @Value("${netty.ip:127.0.0.1}")
    private String ip;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.socketClientTwo.start(ip, port);
    }
}