package com.study.nettyservertest.pipeline;

import com.study.nettyservertest.handler.LengthFieldBasedFrameDecoderServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author lx
 * @date 2023/04/23
 * <p>
 * <p>
 * maxFrameLength 数据最大长度
 * 表示数据的最大长度（包括附加信息、长度标识等内容）
 * <p>
 * lengthFieldOffset 数据长度标识的起始偏移量
 * 用于指明数据第几个字节开始是用于标识有用字节长度的，因为前面可能还有其他附加信息
 * <p>
 * <p>
 * lengthFieldLength 数据长度标识所占字节数（用于指明有用数据的长度）
 * 数据中用于表示有用数据长度的标识所占的字节数
 * <p>
 * lengthAdjustment 长度表示与有用数据的偏移量
 * 用于指明数据长度标识和有用数据之间的距离，因为两者之间还可能有附加信息
 * <p>
 * initialBytesToStrip 数据读取起点
 * 读取起点，不读取 0 ~ initialBytesToStrip 之间的数据
 */
public class NettyChannelLengthFieldInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new LoggingHandler(LogLevel.INFO));

        // 添加 LengthFieldBasedFrameDecoder 长度字段 以解决粘包和拆包问题
        // maxFrameLength 帧的最大长度
        // lengthFieldOffset 内容长度字段的起始位置
        // lengthFieldLength  内容长度的长度
        // lengthAdjustment  额外信息的长度
        // initialBytesToStrip 数据读取起点，我们这里只读取了内容，跳过了长度
        pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 2, 2, 0, 4));


        // 添加自定义处理器
        pipeline.addLast(new LengthFieldBasedFrameDecoderServerHandler());
    }

}
