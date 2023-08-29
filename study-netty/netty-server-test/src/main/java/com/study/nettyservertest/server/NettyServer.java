package com.study.nettyservertest.server;

import com.study.nettyservertest.pipeline.NettyChannelLengthFieldInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {

    private final int port;

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public NettyServer(int port) {
        this.port = port;
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)

                // 与前端进行通信
                //.childHandler(new NettyChannelInitializer());

                // 行解码器  \r\n
                //.childHandler(new NettyChannelLineBaseInitializer());

                // 定长解码器
                //.childHandler(new NettyChannelFixedLengthInitializer());

                // 自定义分隔符
                //.childHandler(new NettyDelimiterBasedInitializer());

                // 长度解码器
                .childHandler(new NettyChannelLengthFieldInitializer());


    }

    public void start() {

        this.future = server.bind(port);

        log.info("netty server server 启动完毕... port = " + port);
    }

    public static void main(String[] args) {
        String s = "HELLO, WORLD";

        byte[] bytes = s.getBytes();
        System.out.println(bytes.length);


    }

}