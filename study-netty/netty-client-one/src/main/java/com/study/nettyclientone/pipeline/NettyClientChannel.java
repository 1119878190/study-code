package com.study.nettyclientone.pipeline;

import com.study.netty.common.decode.MessageDecodeHandler;
import com.study.netty.common.encode.MessageEncodeHandler;
import com.study.nettyclientone.handler.NettyClientHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author: lx
 * @Date: 2023/03/13
 * @Description:
 */

public class NettyClientChannel extends ChannelInitializer<SocketChannel> {



    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        pipeline.addLast(new MessageDecodeHandler());
        pipeline.addLast(new MessageEncodeHandler());
        // 添加上自己的处理器
        pipeline.addLast(new NettyClientHandler());


        //pipeline.addLast(new IdleStateHandler(0, 10, 0));
    }
}
