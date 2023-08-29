package com.study.nettyservertest.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 行解码器对应的自定义处理器
 *
 * @author lx
 * @date 2023/04/19
 */
public class LineBasedFrameDecoderServerHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String) msg;
        System.out.println("服务端收到消息:" + message);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 激活: " + ctx.channel().id().asLongText());
    }
}
