package com.study.nettyservertest.pipeline;

import com.study.nettyservertest.handler.FixedLengthFrameDecoderServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 定长解码器示例
 *
 * @author lx
 * @date 2023/04/19
 */
public class NettyChannelFixedLengthInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new LoggingHandler(LogLevel.INFO));


        pipeline.addLast(new FixedLengthFrameDecoder(5));

        pipeline.addLast(new FixedLengthFrameDecoderServerHandler());

    }
}
