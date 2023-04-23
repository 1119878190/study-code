package com.study.nettyservertest.pipeline;

import com.study.nettyservertest.handler.DelimiterBasedFrameDecoderServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 自定义分隔符
 *
 * @author lx
 * @date 2023/04/19
 */
public class NettyDelimiterBasedInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

        // 自定义分隔符解码器
        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, socketChannel.alloc().buffer().writeBytes("$_123".getBytes())));

        pipeline.addLast(new DelimiterBasedFrameDecoderServerHandler());


    }
}
