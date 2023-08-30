package com.study.netty.common.encode;

import com.alibaba.fastjson.JSONObject;
import com.study.common.dto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * 编码
 *
 * @author dell
 * @date 2023/03/14
 */
public class MessageEncodeHandler extends MessageToMessageEncoder<Message> {

    /**
     * -----------------------------------------------------------------
     * 数据包格式：   type(消息类型) | length(读取的内容长度) | content（json序列化之后的对象）
     * -----------------------------------------------------------------
     *
     * @param channelHandlerContext 通道处理程序上下文
     * @param msg               消息
     * @param out                   出
     * @throws Exception 异常
     */
    //@Override
    //protected void encode(ChannelHandlerContext channelHandlerContext, Message msg, ByteBuf out) throws Exception {
    //
    //
    //    // 先写入消息类型
    //    out.writeInt(msg.getMessageType());
    //    // 再写入消息长度
    //    out.writeInt(msg.getLength());
    //    // 最后写入消息内容字符串
    //    out.writeBytes(msg.getContent().getBytes(StandardCharsets.UTF_8));
    //}

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message msg, List<Object> list) throws Exception {
        String s = JSONObject.toJSONString(msg);
        ByteBuf byteBuf = Unpooled.directBuffer(8+s.length());
        byte[] bytes = s.getBytes();

        // 先写入消息类型
        byteBuf.writeInt(msg.getMessageType());
        // 再写入消息长度
        byteBuf.writeInt(bytes.length -1);
        // 最后写入消息内容字符串
        byteBuf.writeBytes(bytes);
        list.add(new BinaryWebSocketFrame(byteBuf));
    }
}