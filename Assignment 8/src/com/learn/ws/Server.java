package com.learn.ws;

import javax.xml.ws.Endpoint;

public class Server {
	public static void main(String[] args) {
		String url = "http://localhost:8080/ws/calculator";
		Endpoint.publish(url, new Calculator());
		System.out.println("Calculator web service published at " + url);
		System.out.println("WSDL available at " + url + "?wsdl");
	}
}
