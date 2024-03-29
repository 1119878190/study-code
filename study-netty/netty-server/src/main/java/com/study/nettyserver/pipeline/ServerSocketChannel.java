package com.study.nettyserver.pipeline;

import com.study.netty.common.decode.MessageDecodeHandler;
import com.study.nettyserver.handler.NettyServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Socket 初始化器，每一个Channel进来都会调用这里的 InitChannel 方法
 *
 * @author Gjing
 **/
public class ServerSocketChannel extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // websocket 基于http协议，所以要有http编解码器
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        // 对写大数据流的支持
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        // 对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
        // 几乎在netty中的编程，都会使用到此handler
        pipeline.addLast(new HttpObjectAggregator(65535));
        /**
         * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
         * 本handler会帮你处理一些繁重的复杂的事
         * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
         * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 日志
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        // 编码器
        pipeline.addLast(new MessageDecodeHandler());
        // 解码器
        //pipeline.addLast(new MessageEncodeHandler());


        // 自定义处理器
        pipeline.addLast(new NettyServerHandler());

    }
}
