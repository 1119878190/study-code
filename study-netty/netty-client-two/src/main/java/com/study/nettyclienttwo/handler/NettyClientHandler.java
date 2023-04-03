package com.study.nettyclienttwo.handler;

import com.alibaba.fastjson.JSONObject;
import com.study.common.dto.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: lx
 * @Date: 2023/03/13
 * @Description:
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<Message> {


    //当通道就绪就会触发该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client" + ctx);
        String content = "hello 服务端，这是我们第一次链接,我是客户端2";
        int length = content.getBytes().length;
        Message message = new Message(1, length, content);
        ctx.writeAndFlush(message);

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {

        System.out.println("服务器回复的消息：" + JSONObject.toJSONString(message));
        System.out.println("服务器的地址：" + ctx.channel().remoteAddress());
    }
}
