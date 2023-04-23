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

import java.util.Random;

/**
 * 定长解码器 客户端
 *
 * @author lx
 * @date 2023/04/19
 */
public class FixedLengthFrameClient {


    public static char c = 'c';

    public void start() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {

                    // 客户端若设置了字符串解码器  服务端也要字符串解码器  否则是通过 ByteBuf传输
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        //建立连接成功后，会触发active事件
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 发送固定长度
                            int maxLength = 5;
                            for (int i = 0; i < 10; i++) {
                                // 定义字节数组,大小为固定大小，默认填充0
                                byte[] message = new byte[maxLength];
                                Random random = new Random();
                                int i1 = random.nextInt(maxLength);
                                for (int j = 0; j < i1; j++) {
                                    message[j] = (byte) c;
                                }
                                ByteBuf buffer = ctx.alloc().buffer();
                                buffer.writeBytes(message);
                                ctx.writeAndFlush(buffer);
                                c++;
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
}
