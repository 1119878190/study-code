package com.study.nettyservertest.pipeline;

import com.study.nettyservertest.handler.LineBasedFrameDecoderServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 * LineBasedFrameDecoder 行解码器  示例
 *
 * @author lx
 * @date 2023/04/19
 */
public class NettyChannelLineBaseInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new LoggingHandler(LogLevel.INFO));


        // 添加 LineBasedFrameDecoder 行解码器 以解决粘包和拆包问题
        pipeline.addLast(new LineBasedFrameDecoder(1024));

        // 客户端若设置了字符串解码器  服务端也要字符串解码器  否则是通过 ByteBuf传输
        pipeline.addLast(new StringDecoder());

        // 添加自定义处理器
        pipeline.addLast(new LineBasedFrameDecoderServerHandler());
    }
}
