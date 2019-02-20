package com.maven.archetype.netio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class NettyDemo {
    private static int port = 8080;

    public static void main(String[] args) {
        //使用Netty来写， 单线程 多线程 主从线程
        //主从模型
        //Boss线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //Netty服务
        /*
        channel(NioServerSocketChannel.class) 主线程处理类
        childHandler(new ChannelInitializer<SocketChannel>() 子线程处理类Handler
        option(ChannelOption.SO_BACKLOG, 128) 针对主线程的配置
        childOption(ChannelOption.SO_KEEPALIVE,true)  针对子线程的配置
        */

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //无锁化串行编程
                            //client.pipeline().add(XXXHandler());  XXXX extends AAAHandler
                            //client.pipeline().add(XXXHandler()); 登录请求
//                            socketChannel.pipeline().addLast(new HttpResponseEncoder());
                            //解码器
//                            socketChannel.pipeline().addLast(new HttpRequestDecoder());

                            //1 写一个自己的Handler
                            //2 把这个Handler加到pipeline().addLast(Handler)  Handler extends Netty指定的Handler

                            socketChannel.pipeline().addLast(new EchoServerHandler());
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
            //绑定服务端口 阻塞的过程
            ChannelFuture f = b.bind(port).sync();
            System.out.println(NettyDemo.class.getName() + " started and listen on " + f.channel().localAddress());
            //开始接收客户端
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                bossGroup.shutdownGracefully().sync();
                workerGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
