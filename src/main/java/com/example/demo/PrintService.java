package com.example.demo;

import java.util.Map.Entry;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

public class PrintService {

	public Message<String> print(Message<String> message) {
		MessageHeaders headers = message.getHeaders();
		for (Entry<String, Object> entry : headers.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
		System.out.println(message.getPayload());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		return MessageBuilder.withPayload("Message number Received successfully !!!").build();
	}

	public Message<String> printQueue(Message<String> message) {
		System.out.println("Payload :: " + message.getPayload() + " Header :: "
				+ (String) message.getHeaders().get("messageNumaber"));
		String number = (String) message.getHeaders().get("messageNumaber");
		return MessageBuilder.withPayload("Resiving message number " + number + " !!!").build();
	}

	public void printLowerCase(Message<String> message) {
		System.out.println("Payload :: " + message.getPayload().toLowerCase());
	}

	public void printUpperCase(Message<String> message) {
		System.out.println("Payload :: " + message.getPayload().toUpperCase());
	}
}
