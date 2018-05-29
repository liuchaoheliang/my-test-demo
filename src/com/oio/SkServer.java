package com.oio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SkServer {
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ServerSocket server = new ServerSocket(8899);
		
		// server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
        while (true) {
            System.out.println("服务器端正在监听...");
            Socket socket = server.accept();
            String temp = "客户端"+socket.getPort()+":连接";
            System.out.println(temp);
            new Mythread(socket, "server").start();
            for(int i=0 ; i<5;i++){
                Thread.sleep(1000);
                socket.getOutputStream().write("copy that".getBytes());
            }

        }
	}
}
