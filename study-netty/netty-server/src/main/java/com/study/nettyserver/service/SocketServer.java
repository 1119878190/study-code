
package com.study.nettyserver.service;

import com.study.common.dto.Message;
import com.study.nettyserver.handler.ChannelMap;
import com.study.nettyserver.pipeline.ServerSocketChannel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gjing
 **/
@Slf4j
@Component
public class SocketServer {

    @Getter
    private ServerBootstrap serverBootstrap;

    /**
     * netty服务监听端口
     */
    @Value("${netty.port:8088}")
    private int port;



    private final NioEventLoopGroup bossGroup = new NioEventLoopGroup();

    private final NioEventLoopGroup workerGroup = new NioEventLoopGroup();

    @Getter
    public Channel channel;

    /**
     * 启动netty服务器
     */
    @SneakyThrows
    public void start() {
        this.init();
        this.serverBootstrap.bind(this.port);
        log.info("Netty server started on port: {} (TCP)", this.port);
    }

    /**
     * 初始化netty配置
     */
    private void init() {
        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap.group(bossGroup, workerGroup) // 两个线程组加入进来
                .channel(NioServerSocketChannel.class)  // 配置为nio类型
                .option(ChannelOption.SO_BACKLOG, 10240) // 服务端可连接队列大小
                .option(ChannelOption.SO_REUSEADDR, true) // 参数表示允许重复使用本地地址和端口
                .childOption(ChannelOption.TCP_NODELAY, true) // 是否禁用Nagle算法 简单点说是否批量发送数据 true关闭 false开启。 开启的话可以减少一定的网络开销，但影响消息实时性
                .childOption(ChannelOption.SO_KEEPALIVE, true) // 保活开关2h没有数据服务端会发送心跳包
                .childHandler(new ServerSocketChannel()); // 加入自己的初始化器
    }


    /**
     * 发送消息到全部的channel
     *
     * @param message 消息
     */
    public void sendMessage(Message message){
        ConcurrentHashMap<String, Channel> channelMap = ChannelMap.getChannelMap();
        for (Map.Entry<String, Channel> stringChannelEntry : channelMap.entrySet()) {
            Channel value = stringChannelEntry.getValue();
            value.writeAndFlush(message);
        }

    }

    /**
     * 发送消息到全部的channel
     *
     * @param message 消息
     */
    public void sendMessageByChannel(Message message,String channelId){
        Channel channel = ChannelMap.getChannel(channelId);
        if (Objects.nonNull(channel)){
            channel.writeAndFlush(message);
        }

    }
}