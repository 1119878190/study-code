package com.study.nettyservertest.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 行解码器对应的客户端
 *
 * @author liuxu
 * @date 2023/04/19
 */
public class LineBasedFrameClient {


    public static void main(String[] args) {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {

                    // 客户端若设置了字符串解码器  服务端也要字符串解码器  否则是通过 ByteBuf传输
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                        //建立连接成功后，会触发active事件
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            // 发送带有分隔符的数据包
                            for (int i = 0; i < 10; i++) {
                                String msg = "hello" + i + "\r\n";
                                ctx.writeAndFlush(msg);
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
