package com.study.nettyserver.pipeline;

import com.study.netty.common.decode.MessageDecodeHandler;
import com.study.netty.common.encode.MessageEncodeHandler;
import com.study.nettyserver.handler.NettyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Socket 初始化器，每一个Channel进来都会调用这里的 InitChannel 方法
 *
 * @author Gjing
 **/
public class ServerSocketChannel extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        pipeline.addLast("MessageDecodeHandler",new MessageDecodeHandler());
        pipeline.addLast("MessageEncodeHandler",new MessageEncodeHandler());
        // 加入 netty 提供的 IdleStateHandler 用于心跳
                            /*
                            1.IdleStateHandler 是 netty 提供的处理空闲状态的处理器
                            2.参数说明：
                                long readerIdleTime：表示服务端多长时间没有读数据，就会发送一个心跳检测包检测是否连接
                                long writerIdleTime：表示服务端多长时间没有写数据，就会发送一个心跳检测包检测是否连接
                                long allIdleTime：表示服务端多长时间没有 读/写 数据，就会发送一个心跳检测包检测是否连接
                                TimeUnit unit：时间单位
                            3.当 IdleStateEvent 触发后，就会传递给管道的下一个handler去处理，
                              通过调用(触发)handler 的 userEventTiggered，在该方法中去处理
                              IdleStateEvent(读空闲、写空闲、读写空闲)
                             */
        //pipeline.addLast(new IdleStateHandler(60, 30, 60 * 30, TimeUnit.SECONDS));
        //  配置通道处理 来进行业务处理
        pipeline.addLast(new NettyServerHandler());

    }
}
