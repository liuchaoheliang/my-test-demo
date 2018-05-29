package com.nio.netty;

import java.util.Scanner;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

@Sharable     
public class EchoClientHandler extends SimpleChannelInboundHandler  { 

	public void channelActive(final ChannelHandlerContext ctx) throws InterruptedException {
        ctx.writeAndFlush(Unpooled.copiedBuffer(" hello , Netty rocks!", CharsetUtil.UTF_8));   // 当被通知Channel是活跃的时候，发送一条消息
		System.out.println("============active！");
		//EchoClient.chat();
//		System.out.print("============请输入发送信息：");
//		Scanner cin=new Scanner(System.in);
//		String name=cin.nextLine();
//		if(!"exit".equals(name)){
//			final ChannelFuture f = ctx.writeAndFlush(Unpooled.copiedBuffer(name, CharsetUtil.UTF_8));
//			f.addListener(new ChannelFutureListener() {
//				@Override
//				public void operationComplete(ChannelFuture future) throws Exception {
//					assert f == future;
//					System.out.println("=========消息发送完成");
//					//ctx.close();
//				}
//			});
//		}else{
//			ctx.close();
//		}
    }
	
	@Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("=======read complete ok");
        ctx.flush();
    } 
	
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) {    //在发生异常时，记录错误并关闭Channel 
        cause.printStackTrace();
        ctx.close();
    }

//	@Override
//	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		 ByteBuf buf = (ByteBuf) msg;
//	     System.out.println("=========client read msg:"+buf.toString(CharsetUtil.UTF_8));
//	     ctx.close();
//		 EchoClient.chat();
//	}
	
	
//    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
    	ByteBuf buf = (ByteBuf) msg;
		System.out.println("==========Client received msg: " + buf.toString(CharsetUtil.UTF_8));
//		ctx.close();
		EchoClient.chat();
//		EchoClient.getMe().start();
	}
    
}
