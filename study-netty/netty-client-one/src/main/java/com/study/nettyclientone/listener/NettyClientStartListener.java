package com.study.nettyclientone.listener;

import com.study.nettyclientone.service.SocketClientOne;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class NettyClientStartListener implements ApplicationRunner {
    @Resource
    private SocketClientOne socketClientOne;
    /**
     * netty服务监听端口
     */
    @Value("${netty.port:8088}")
    private int port;

    @Value("${netty.ip:127.0.0.1}")
    private String ip;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.socketClientOne.start(ip, port);
    }
}