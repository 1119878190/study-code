package com.study.netty.common.decode;

import com.study.common.dto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class MessageDecodeHandler extends ByteToMessageDecoder {


    private static final int MESSAGE_HEADER_SIZE = 17; // 消息头部长度

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
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> list) throws Exception {

        // 如果数据长度小于8，说明数据不完整，等待下一次读取
        // 8byte = 类型4字节 + 长度4字节
        if (in.readableBytes() < 8) {
            return;
        }
        in.markReaderIndex(); // 标记读取位置，以便恢复

        // 读取消息类型
        int type = in.readInt();
        // 读取消息长度
        int length = in.readInt();

        // 如果数据长度不足，说明数据不完整，等待下一次读取
        if (in.readableBytes() < length) {
            // 恢复读取位置
            in.resetReaderIndex();
            return;
        }
        byte[] contentBytes = new byte[length];
        // 读取消息内容
        in.readBytes(contentBytes);
        String content = new String(contentBytes, StandardCharsets.UTF_8);

        Message msg = new Message();
        msg.setMessageType(type);
        msg.setLength(length);
        msg.setContent(content);

        list.add(msg);


    }
}