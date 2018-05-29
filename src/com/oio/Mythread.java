package com.oio;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

public class Mythread extends Thread {

	private Socket socket;
	private String type ;
	
	public Mythread(Socket socket, String type) {
		this.socket = socket;
		this.type = type;
	}

	@Override
	public void run() {
		while(true){
			try {
				Reader reader = new InputStreamReader(socket.getInputStream());
				int len = 0; 
				char chars[] = new char[512];
				while ((len = ((Reader) reader).read(chars)) != -1) {
					String  temp = new String(chars, 0, len);
                    System.out.println("来自"+type+",端口"+socket.getPort()+"的消息:" +temp);
                }
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
