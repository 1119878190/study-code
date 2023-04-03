package com.study.netty.common.encode;

import com.study.common.dto.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * 编码
 *
 * @author dell
 * @date 2023/03/14
 */
public class MessageEncodeHandler extends MessageToByteEncoder<Message> {

    /**
     * -----------------------------------------------------------------
     * 数据包格式：   type(消息类型) | length(读取的内容长度) | content（json序列化之后的对象）
     * -----------------------------------------------------------------
     *
     * @param channelHandlerContext 通道处理程序上下文
     * @param message               消息
     * @param out                   出
     * @throws Exception 异常
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf out) throws Exception {


        out.writeInt(message.getMessageType());
        // 为了方便测试，这个直接计算了content的length，通常是从message中取
        out.writeInt(message.getContent().getBytes().length);
        out.writeBytes(message.getContent().getBytes(CharsetUtil.UTF_8));
    }
}