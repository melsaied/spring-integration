package com.example.demo;

import java.util.concurrent.Future;

import org.springframework.messaging.Message;

public interface PrintGatewayPriority {
	public Future<Message<String>> print(Message<String> message);
}
