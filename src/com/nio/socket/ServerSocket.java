package com.nio.socket;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerSocket {

	public static void main(String[] args) throws IOException {
//		RandomAccessFile aFile = new RandomAccessFile("D:/send.sql", "rw");
//
//		FileChannel inChannel = aFile.getChannel();
//		File file = new File("");
//
//		ByteBuffer buf = ByteBuffer.allocate(48);
//
//		int bytesRead = 0;
//
//		while ( (bytesRead = inChannel.read(buf)) != -1) {
//
//			System.out.println("Read " + bytesRead);
//
//			buf.flip();
//
//			while (buf.hasRemaining()) {
//
//				System.out.print((char) buf.get());
//
//			}
//
//			buf.clear();
//
//		}
//
//		aFile.close();
		test();
		
	}
	
	
	public static void test() throws IOException{
		
		Selector selector = Selector.open();
		
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		serverSocketChannel.socket().bind(new InetSocketAddress(8099));
		serverSocketChannel.configureBlocking(false);
		
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		
		while(true) {
		  //获取已经就绪的通道数量
		  int readyChannels = selector.select();
		  if(readyChannels == 0) continue;
		  
		  //根据key取到SelectionKey 从而获取到通道相关信息
		  Set<SelectionKey> selectedKeys = selector.selectedKeys();
		  Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
		  
		  while(keyIterator.hasNext()) {
		    SelectionKey key = keyIterator.next();
		    if(key.isAcceptable()) {
		    	ServerSocketChannel channel = (ServerSocketChannel) key.channel();
		    	SocketChannel socketChannel =  channel.accept();
		    	socketChannel.configureBlocking(false);
		    	socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
		    	System.out.println("接收到新链接："+socketChannel.getRemoteAddress());
		    } else if (key.isConnectable()) {
		    	System.out.println("a connection was established with a remote server.");
		        // a connection was established with a remote server.
		    } else if (key.isReadable()) {
		    	SocketChannel socketChannel =  (SocketChannel) key.channel();
		    	ByteBuffer buf = ByteBuffer.allocate(48);
		    	int bytesRead = socketChannel.read(buf) ;
		    	if(bytesRead == -1 || bytesRead == 0 ){
		    		break;
		    	}
		    	StringBuilder data = new StringBuilder();
				while ( bytesRead != -1 && bytesRead > 0) {
					System.out.println("Read " + bytesRead);
					buf.flip();
					data.append(new String (buf.array()));
					
					//如果缓冲区还有内容 
//					while (buf.hasRemaining()) {
//						//每一次get ,buf的pos会自动增加+1
//						System.out.print((char)buf.get());
//					}
					buf.clear();
					bytesRead = socketChannel.read(buf) ;
				}
				System.out.println("接收到"+socketChannel.getRemoteAddress()+"消息："+data.toString());
		        // a channel is ready for reading
		    } else if (key.isWritable()) {
		        // a channel is ready for writing
		    	//System.out.println("a channel is ready for writing");
		    }
		    keyIterator.remove();
		  }
		}
		
	}
	
	
	

}
