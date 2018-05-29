package com.nio.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.channels.Pipe.SinkChannel;
import java.nio.channels.Pipe.SourceChannel;

public interface PipeTest {
	public static void main(String[] args) throws IOException {
		
		Pipe pipe = Pipe.open();
		
		//写
		Pipe.SinkChannel sinkChannel = pipe.sink();
		
		//读
		Pipe.SourceChannel sourceChannel = pipe.source();
		
		new ThreadB(sourceChannel).start();

		new ThreadA(sinkChannel).start();	
		
	}
	
	
	
	class ThreadA extends Thread {
		private SinkChannel sinkChannel;

		public ThreadA(SinkChannel sinkChannel) {
			this.sinkChannel = sinkChannel;
		}

		@Override
		public void run() {
			String s = "what are you doing ?";
			ByteBuffer buffer = ByteBuffer.allocate(256);
			while(true){
				buffer.clear();
				buffer.put(s.getBytes());
				buffer.flip();
				try {
					this.sleep(5000);
					sinkChannel.write(buffer);
					System.out.println("发送线程，发送信息");
				} catch (IOException | InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	
	class ThreadB extends Thread {
		private SourceChannel sourceChannel;

		public ThreadB(SourceChannel sourceChannel) {
			this.sourceChannel = sourceChannel;
		}

		@Override
		public void run() {
			ByteBuffer buffer = ByteBuffer.allocate(256);
			while(true){
				try {
					while(sourceChannel.read(buffer) != -1 ){
						buffer.flip();
						System.out.println("接收到 copy : "+ new String (buffer.array()).trim());
						buffer.clear();
					}
//					
//					int num = sourceChannel.read(buffer) ;
//					if(num == -1 ){
//						continue;
//					}
//					
//					while( num != -1 ){
//						StringBuilder data = new StringBuilder();
//						buffer.flip();
//						data.append(new String (buffer.array()).trim());
//						
//						buffer.clear();
//						num = sourceChannel.read(buffer) ;
//						System.out.println("接收到 copy : "+ data);
//					}
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
}
