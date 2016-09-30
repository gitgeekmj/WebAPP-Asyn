package com.server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * Server implementation
 * 
 * 
 */
public class HttpServer implements Runnable{
	// GetProperty("user.dir") used to get the current working directory;
	// File.separator used to add the system directory separator£¬convenient
	// cross-platform
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "WebRoot";
	// Use to define shutdown server directives
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private static boolean shutdown = false;
	private ServerSocket serverSocket = null;
	private ExecutorService pool;
	
	public static boolean getShutdown(){
		return shutdown;
	}
	
	public static void setShutdown(boolean shutdown) {
		HttpServer.shutdown = shutdown;
	}

	public static String getShutdownCommand() {
		return SHUTDOWN_COMMAND;
	}
	
	public HttpServer(int port,int poolSize){
		try {
			serverSocket = new ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));
			pool = Executors.newFixedThreadPool(poolSize);;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			for (;;) {
				pool.execute(new Handler(serverSocket.accept()));
			}
		} catch (IOException e) {
			e.printStackTrace();
			pool.shutdown();
		}
	}
	

	public static void main(String[] args) {
		int port = 8080;
		HttpServer server = new HttpServer(port,10);
		server.run();
	}
}
