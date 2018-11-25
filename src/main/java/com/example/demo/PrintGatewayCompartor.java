package com.example.demo;

import java.util.concurrent.Future;

import org.springframework.messaging.Message;

public interface PrintGatewayCompartor {
	public Future<Message<String>> print(Message<String> message);
}
