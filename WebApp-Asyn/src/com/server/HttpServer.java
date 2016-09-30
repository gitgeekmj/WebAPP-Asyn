package com.server;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
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
	private boolean shutdown = false;

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
			InputStream input = null;
			OutputStream output = null;
			try {
				// accept() method:intercept and accepts connections for this
				// socket,which blocks until the connection is passed in
				socket = serverSocket.accept();
				// Get the input stream from the socket
				input = socket.getInputStream();
				// Get the output stream from the socket
				output = socket.getOutputStream();
				// Create Request object and parse
				Request request = new Request(input);
				request.parse();
				// Create Response object
				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResource();
				//close socket  
                socket.close();  
                //If the request command is "/SHUTDOWN",the server is shut down
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
