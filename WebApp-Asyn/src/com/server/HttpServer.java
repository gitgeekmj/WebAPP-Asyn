package com.server;

import java.io.File;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Server implementation
 * 
 * 
 */
public class HttpServer {
	// GetProperty("user.dir") used to get the current working directory;
	// File.separator used to add the system directory separator£¬convenient
	// cross-platform
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "WebRoot";
	// Use to define shutdown server directives
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	private static boolean shutdown = false;
	
	public static boolean getShutdown(){
		return shutdown;
	}
	
	public static void setShutdown(boolean shutdown) {
		HttpServer.shutdown = shutdown;
	}

	public static String getShutdownCommand() {
		return SHUTDOWN_COMMAND;
	}

	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		server.start();
	}

	public void start() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		while (!shutdown) {
			Socket socket = null;
			try {
				// accept() method:intercept and accepts connections for this
				// socket,which blocks until the connection is passed in
				socket = serverSocket.accept();
				// Get the input stream from the socket
				
				Thread workThread = new Thread(new Handler(socket));
				workThread.start();
				
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
