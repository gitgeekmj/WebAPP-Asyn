package com.server;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * Request class that a HTTP request,you can pass InputStream object to create
 * the Request object. you can call the read() method in the InputStream object
 * to read the raw data for the HTTP request.
 *
 */
public class Request {
	private InputStream input;
	private String uri;

	public Request(InputStream input) {
		this.input = input;
	}

	// The parse() method is used to parse the raw data of the HTTP request,and
	// the parse() method calls the parseUri() method to parse the HTTP
	// request's URI.
	public void parse() {
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			// Call read() method from the input to read a certain amount of
			// bytes,and stored in the buffer array "buffer".
			i = input.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
		}
		for (int j = 0; j < i; j++) {
			request.append((char) buffer[j]);
		}
		System.out.print(request.toString());
		uri = parseUri(request.toString());
	}

	/*
	 * 
	 * "requestString" form is as follows: GET /index.html HTTP/1.1 Host:
	 * localhost:8080 Connection: keep-alive Cache-Control: max-age=0 ... The
	 * purpose of this function is to get the "/index.html"string.
	 */
	private String parseUri(String requestString) {
		int index1, index2;
		index1 = requestString.indexOf(" ");
		if (index1 != -1) {
			index2 = requestString.indexOf(" ", index1 + 1);
			if (index2 > index1) {
				return requestString.substring(index1 + 1, index2);
			}
		}
		return null;
	}

	// Call the public method getUri() returns the requested uri.
	public String getUri() {
		return this.uri;
	}
}
