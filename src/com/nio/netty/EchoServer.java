package com.nio.netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " ");
//        }
//        int port = Integer.parseInt(args[0]); //设置端口值（如果端口参数的格式不正确，则抛出一个NumberFormatException）
    	int port = Integer.parseInt("12121");
        new EchoServer(port).start();   //调用服务器的start()方法
    }
    public void start() throws Exception {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();  //创建Event-LoopGroup
        //EventLoopGroup group = new OioEventLoopGroup();
        try {
             ServerBootstrap b = new ServerBootstrap();   //创建Server-Bootstrap
             b = b.option(ChannelOption.SO_BACKLOG, 128);
             b = b.childOption(ChannelOption.SO_KEEPALIVE, true);
             
             b.group(group)
                 .channel(NioServerSocketChannel.class) //指定所使用的NIO传输Channel
                 .localAddress(new InetSocketAddress(port)) //使用指定的端口设置套接字地址
                .childHandler(new ChannelInitializer(){   //添加一个EchoServer-Handler到子Channel的ChannelPipeline
					@Override
					protected void initChannel(Channel ch) throws Exception {
						//websocket　 handle
						//ch.pipeline().addLast("http-codec",new HttpServerCodec());
						//ch.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
						//ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
						//ch.pipeline().addLast("handler",new MyWebSocketServerHandler());
						
						ch.pipeline().addLast("handler",serverHandler);
						//ch.pipeline().addLast(serverHandler); // EchoServerHandler被标注为@Shareable，所以我们可以总是使用同样的实例	
					}
                });
            ChannelFuture f = b.bind().sync();   //异步地绑定服务器；调用sync()方法阻塞等待直到绑定完成
            System.out.println("启动成功...");
            f.channel().closeFuture().sync();//获取Channel的CloseFuture，并且阻塞当前线程直到它完成
        } finally {
            group.shutdownGracefully().sync();   //关闭EventLoopGroup，释放所有的资源
        }
    }
}