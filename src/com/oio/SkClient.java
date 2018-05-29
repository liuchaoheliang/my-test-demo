package com.oio;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SkClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket client = new Socket("127.0.0.1", 8899);
		  new Mythread(client, "client").start();
		  
		  for(int i=0;i<5;i++){
			  client.getOutputStream().write("hello word !! ".getBytes());
		  }
		
	}
}
