package com.study.nettyservertest.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;

/**
 *
 * 长度字段解码器--客户端
 *
 * @author lx
 * @date 2023/04/23
 */
public class LengthFieldBasedFrameClient {

    public void start() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {

                    // 通过 ByteBuf传输
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        //建立连接成功后，会触发active事件
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 发送带有分隔符的数据包
                            for (int i = 0; i < 10; i++) {

                                ByteBuf buffer = ch.alloc().buffer();
                                send(buffer, "你今天心情怎么样" + i);
                                ctx.writeAndFlush(buffer);
                            }
                        }
                    });
                }
            });
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8088).sync();
            //释放连接
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            System.out.println("client error :" + e);
        } finally {
            //释放EventLoopGroup
            worker.shutdownGracefully();
        }
    }


    private static void send(ByteBuf buf, String msg) {
        // 得到数据的长度
        int length = msg.length();
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        // 将数据信息写入buf
        // 写入长度标识前的其他信息
        buf.writeShort(1);
        // 写入数据长度标识
        buf.writeShort(bytes.length);
        // 写入具体的数据
        buf.writeBytes(bytes);
    }


}
