package com.learn.ws;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "Calculator")
public class Calculator {

	@WebMethod(operationName = "add")
	public int add(@WebParam(name = "a") int a, @WebParam(name = "b") int b) {
		return a + b;
	}

	@WebMethod(operationName = "subtract")
	public int subtract(@WebParam(name = "a") int a, @WebParam(name = "b") int b) {
		return a - b;
	}

	@WebMethod(operationName = "multiply")
	public int multiply(@WebParam(name = "a") int a, @WebParam(name = "b") int b) {
		return a * b;
	}

	@WebMethod(operationName = "divide")
	public int divide(@WebParam(name = "a") int a, @WebParam(name = "b") int b) {
		if (b == 0) {
			throw new ArithmeticException("Cannot divide by zero");
		}
		return a / b;
	}
}
