package com.study.netty.common.encode;

import com.study.common.dto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;

public class MessageCodec extends LengthFieldBasedFrameDecoder {

    public MessageCodec(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in == null) {
            return null;
        }
        if (in.readableBytes() < 8) {  // 最小消息长度为8（即messageType + length）
            throw new RuntimeException("消息头长度不足");
        }
        in.markReaderIndex();
        int msgType = in.readInt();  // 读取消息类型
        int length = in.readInt();  // 读取消息长度
        if (in.readableBytes() < length) {  // 消息体长度不足，重置读指针
            in.resetReaderIndex();
            return null;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);  // 读取消息体
        String content = new String(bytes, StandardCharsets.UTF_8);
        return new Message(msgType, length, content);  // 封装为Message对象返回
    }





    @Override
    protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        return super.extractFrame(ctx, buffer, index, length);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
