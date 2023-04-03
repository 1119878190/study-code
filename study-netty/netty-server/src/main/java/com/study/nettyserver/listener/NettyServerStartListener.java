package com.study.nettyserver.listener;

import com.study.nettyserver.service.SocketServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 监听Spring容器启动完成，完成后启动Netty服务器
 * @author Gjing
 **/
@Component
public class NettyServerStartListener implements ApplicationRunner {
    @Resource
    private SocketServer socketServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.socketServer.start();
    }
}
