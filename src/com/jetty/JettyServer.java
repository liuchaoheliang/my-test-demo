package com.jetty;

import java.util.concurrent.ThreadPoolExecutor;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandler.Context;
import org.eclipse.jetty.server.handler.GzipHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class JettyServer {
	
	public static void main(String[] args) throws Exception {
		
		QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(500);
        
		//Server server = new Server(threadPool);
		Server server = new Server(8080);
		
		ContextHandler context = new ContextHandler();
		context.setContextPath("/");
		context.setHandler(new HelloWorld());
		//context.set(new HelloWorld());
		//ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
//        context.setContextPath("/");
// 		      增加一个默认的servlet
//        context.addServlet(DefaultServlet.class, "/");
//		GzipHandler handler
		server.setHandler(context);
		server.start();
		server.join();
	}
}
