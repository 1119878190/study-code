package com.study.nettyservertest.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义分隔符 handler
 *
 * @author lx
 * @date 2023/04/19
 */
public class DelimiterBasedFrameDecoderServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        String message = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("收到消息: " + message);
    }
}
