package com.example.demo;

import org.springframework.messaging.Message;

public interface PrintGatewayDirect {
	public Message<String> print(Message<String> message);
}
