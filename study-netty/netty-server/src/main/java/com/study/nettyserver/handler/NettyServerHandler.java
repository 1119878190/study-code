package com.study.nettyserver.handler;

import com.alibaba.fastjson.JSONObject;
import com.study.common.dto.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Socket拦截器，用于处理客户端的行为
 *
 * @author Gjing
 **/
@Slf4j
@Component
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {


    int readIdleTimes = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        log.info("收到客户端发来的消息: " + JSONObject.toJSONString(message));
        ctx.channel().writeAndFlush(message );
    }


    //@Override
    //public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    //    IdleStateEvent event = (IdleStateEvent) evt;
    //
    //    String eventType = null;
    //    switch (event.state()) {
    //        case READER_IDLE:
    //            eventType = "读空闲";
    //            readIdleTimes++; // 读空闲的计数加1
    //            break;
    //        case WRITER_IDLE:
    //            eventType = "写空闲";
    //            // 不处理
    //            break;
    //        case ALL_IDLE:
    //            eventType = "读写空闲";
    //            // 不处理
    //            break;
    //    }
    //
    //    System.out.println(ctx.channel().remoteAddress() + "超时事件：" + eventType);
    //    if (readIdleTimes > 3) {
    //        System.out.println(" [server]读空闲超过3次，关闭连接，释放更多资源");
    //        ctx.channel().writeAndFlush("idle close");
    //        ctx.channel().close();
    //    }
    //}

    /**
     * 设备接入连接时处理
     *
     * @param ctx
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("新的客户端链接：" + ctx.channel().id().asLongText());

        ChannelMap.getChannelMap().put(ctx.channel().id().asLongText(), ctx.channel());


    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端:" + ctx.channel().id().asLongText() + "断开链接");
        ChannelMap.getChannelMap().remove(ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
        ChannelMap.getChannelMap().remove(ctx.channel().id().asLongText());
    }


}