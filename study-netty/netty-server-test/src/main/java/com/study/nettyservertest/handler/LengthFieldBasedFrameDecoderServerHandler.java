package com.study.nettyservertest.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @author lx
 * @date 2023/04/23
 */
public class LengthFieldBasedFrameDecoderServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf message = (ByteBuf) msg;
        String s = message.toString(StandardCharsets.UTF_8);

        System.out.println("服务端收到消息: " + s);
    }
}
