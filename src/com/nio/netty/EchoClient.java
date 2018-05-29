package com.nio.netty;

import java.net.InetSocketAddress;
import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    
    private static EchoClient me;
    
    private static ChannelFuture f = null;

    public static EchoClient getMe() {
		return me;
	}

	public static void setMe(EchoClient me) {
		EchoClient.me = me;
	}

	public void start() throws Exception {
       EventLoopGroup group = new NioEventLoopGroup();
        try {   
        	//  创建Bootstrap
            Bootstrap b = new Bootstrap();    //  指定EventLoopGroup以处理客户端事件；需要适用于NIO的实现
            b.option(ChannelOption.SO_KEEPALIVE, true); 
            b.group(group)    
                 .channel(NioSocketChannel.class)    //  适用于NIO传输的Channel类型
                 .remoteAddress(new InetSocketAddress(host, port))    //  设置服务器的InetSocketAddr-ess![](/api/storage/getbykey/screenshow?key=17043add7e9c14a5d3f7)                
                 .handler(new ChannelInitializer<SocketChannel>() {   //  在创建Channel时，向ChannelPipeline中添加一个Echo-ClientHandler实例
                	 @Override
                	 public void initChannel(SocketChannel ch) throws Exception {
                		 ch.pipeline().addLast(new EchoClientHandler());
                     }
                	 
                });
            
            f = b.connect().sync();    //  连接到远程节点，阻塞等待直到连接完成
            f.channel().closeFuture().sync();     //  阻塞，直到Channel关闭
            System.out.println("=======close connect!!");
        } finally {
        	System.out.println("=======clean resource !!");
            group.shutdownGracefully().sync();      //  关闭线程池并且释放所有的资源
        }
    }
	
	public static void chat(){
		System.out.print("============请输入发送信息：");
		Scanner cin=new Scanner(System.in);
		String name=cin.nextLine();
		if(!"exit".equals(name)){
			f.channel().writeAndFlush(Unpooled.copiedBuffer(name, CharsetUtil.UTF_8));
			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					assert f == future;
					System.out.println("=========消息发送完成");
					//f.channel().close();
				}
			});
		}else{
			f.channel().close();
		}
	}

    public static void main(String[] args) throws Exception {
//        if (args.length != 2) {
//            System.err.println(
//                "Usage: " + EchoClient.class.getSimpleName() +
//                " <host> <port>");
//            return;
//        }

//        String host = args[0];
//        int port = Integer.parseInt(args[1]);
    	String host = "127.0.0.1";
    	int port = 12121;
       new EchoClient(host, port).start();
       // me.start();
    }
}