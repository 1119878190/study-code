package com.study.nettyservertest.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler {


    /**
     * 通道read0
     *
     * @param channelHandlerContext 通道处理程序上下文
     * @param textWebSocketFrame    文本框架网络套接字
     * @throws Exception 异常
     */
    //@Override
    //protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
    //    String text = textWebSocketFrame.text();
    //    log.info("服务端收到消息:" + text);
    //    channelHandlerContext.writeAndFlush(new TextWebSocketFrame(text));
    //
    //}
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel 激活" + ctx.channel().id().asLongText());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 发生异常时关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端断开连接" + ctx.channel().id().asLongText());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        String message = (String) msg;
        System.out.println("接收到消息: " + message);
    }
}