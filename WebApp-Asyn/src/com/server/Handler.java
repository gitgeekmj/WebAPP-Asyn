package com.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Handler implements Runnable {
	private Socket socket;

	public Handler(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			Request request = new Request(input);
			request.parse();
			Response response = new Response(output);
			response.setRequest(request);
			response.sendStaticResource();
			boolean shutdown = HttpServer.getShutdown();
			shutdown = request.getUri().equals(HttpServer.getShutdownCommand());
			HttpServer.setShutdown(shutdown);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket == null)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
