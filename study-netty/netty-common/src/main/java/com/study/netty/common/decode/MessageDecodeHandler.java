package com.study.netty.common.decode;

import com.study.common.dto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class MessageDecodeHandler extends MessageToMessageDecoder<BinaryWebSocketFrame> {


    private static final int MESSAGE_HEADER_LENGTH = 8; // 消息头部长度为 8 字节（消息类型 + 消息长度）

    /**
     * 解码
     * <p>
     * 数据包格式：
     * <p>
     * |   4 byte   |   4 byte   |    n byte   |
     * |------------|------------|-------------|
     * |  type      |  length    |  content    |
     *
     * @param channelHandlerContext 通道处理程序上下文
     * @param in                    在
     * @param list                  列表
     * @throws Exception 异常
     */
    //@Override
    //protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
    //
    //    if (in.readableBytes() < MESSAGE_HEADER_LENGTH) {
    //        // 如果可读字节数不足 MESSAGE_HEADER_LENGTH，说明数据不完整，暂时不做处理
    //        return;
    //    }
    //    in.markReaderIndex(); // 标记当前读位置，以便重试
    //
    //    // 读取消息类型和长度
    //    int messageType = in.readInt();
    //    int length = in.readInt();
    //    if (in.readableBytes() < length) {
    //        // 如果可读字节数不足消息长度，说明数据不完整，暂时不做处理
    //        in.resetReaderIndex(); // 重置读位置
    //        return;
    //    }
    //
    //    // 读取消息内容字符串
    //    byte[] contentBytes = new byte[length];
    //    in.readBytes(contentBytes);
    //    String content = new String(contentBytes, StandardCharsets.UTF_8);
    //
    //    // 创建 Message 对象并添加到 out 列表中
    //    Message msg = new Message();
    //    msg.setMessageType(messageType);
    //    msg.setLength(length);
    //    msg.setContent(content);
    //    out.add(msg);
    //
    //
    //}

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, BinaryWebSocketFrame binaryWebSocketFrame, List<Object> list) throws Exception {

        ByteBuf in = binaryWebSocketFrame.content();

        if (in.readableBytes() < MESSAGE_HEADER_LENGTH) {
            // 如果可读字节数不足 MESSAGE_HEADER_LENGTH，说明数据不完整，暂时不做处理
            return;
        }
        in.markReaderIndex(); // 标记当前读位置，以便重试

        // 读取消息类型和长度
        int messageType = in.readInt();
        int length = in.readInt();
        if (in.readableBytes() < length) {
            // 如果可读字节数不足消息长度，说明数据不完整，暂时不做处理
            in.resetReaderIndex(); // 重置读位置
            return;
        }

        // 读取消息内容字符串
        byte[] contentBytes = new byte[length];
        in.readBytes(contentBytes);
        String content = new String(contentBytes, StandardCharsets.UTF_8);

        // 创建 Message 对象并添加到 out 列表中
        Message msg = new Message();
        msg.setMessageType(messageType);
        msg.setLength(length);
        msg.setContent(content);
        list.add(msg);
    }
}