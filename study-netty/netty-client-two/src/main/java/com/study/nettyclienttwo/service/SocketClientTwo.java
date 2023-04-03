package com.study.nettyclienttwo.service;

import com.study.common.dto.Message;
import com.study.nettyclienttwo.pipeline.NettyClientChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: lx
 * @Date: 2023/03/13
 * @Description:
 */
@Component
@Slf4j
public class SocketClientTwo {


    //注意客户端使用的不是ServerBootstrap 而是Bootstrap
    private Bootstrap bootstrap;

    /**
     * netty服务监听端口
     */
    @Value("${netty.port:8088}")
    private int port;
    /**
     * 主线程组数量
     */
    @Value("${netty.bossThread:1}")
    private int bossThread;


    private Channel channel;

    /**
     * 启动netty服务器
     */
    @SneakyThrows
    public void start(String ip, Integer port) {
        this.init();
        //ChannelFuture future = this.bootstrap.connect(ip, port).sync();
        ChannelFuture future = this.bootstrap.connect(ip, port);
        channel = future.channel();
        // 心跳检测
        //while (channel.isActive()) {
        //    Thread.sleep(3 * 1000);
        //    channel.writeAndFlush("Heartbeat Packet");
        //}
        log.info("Netty client started on port: {} (TCP) with boss thread {}", this.port, this.bossThread);
    }

    /**
     * 初始化netty配置
     */
    private void init() {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)// 配置为nio类型
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new NettyClientChannel()); // 加入自己的初始化器
    }

    public void sendMessage(Message message) {
        channel.writeAndFlush(message);
    }

}
