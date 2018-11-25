package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
@Configuration
@ImportResource("spring-integration.xml")
public class SpringIntegrationApplication implements ApplicationRunner {

	@Autowired
	@Qualifier("messageChannel")
	private DirectChannel messageChannel;

	@Autowired
	@Qualifier("inputChannel")
	private DirectChannel inputChannel;

	@Autowired
	@Qualifier("outputChannel")
	private DirectChannel outputChannel;

	@Autowired
	@Qualifier("sendAndReceiveChannel")
	private DirectChannel sendAndReceiveChannel;

	@Autowired
	private PrintGatewayQueue printGatewayQueue;

	@Autowired
	private PrintGatewayPriority printGatewayPriority;

	@Autowired
	private PrintGatewayCompartor printGatewayCompartor;

	@Autowired
	private PrintGatewayDirect gatewayDirect;

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
//		printMessages();
//		printMessages2();
//		printChannels();
//		printEndpoints();
//		printMessagingTemplate();
//		printQueueChannel();
//		printPriorityChannel();
//		printCompartorChannel();
		printDirctChannel();
	}

	private void printMessages() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", "value");
		MessageHeaders headers = new MessageHeaders(map);
		Message<String> message = new GenericMessage<String>("Hello Spring Integration 1", headers);
		new PrintService().print(message);
	}

	private void printMessages2() {
		Message<String> message = MessageBuilder.withPayload("Hello Spring Integration 2").setHeader("key0", "value0")
				.setHeader("key1", "value1").setHeader("key2", "value2").build();
		new PrintService().print(message);
	}

	private void printChannels() {
		messageChannel.subscribe(new MessageHandler() {
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message<?> message) throws MessagingException {
				new PrintService().print((Message<String>) message);
			}
		});
		Message<String> message = MessageBuilder.withPayload("Hello Spring Integration 3").setHeader("key3", "value3")
				.build();
		messageChannel.send(message);
	}

	private void printEndpoints() {
		Message<String> message = MessageBuilder.withPayload("Hello Spring Integration 4").setHeader("key4", "value4")
				.build();
		messageChannel.send(message);
	}

	private void printMessagingTemplate() {
		Message<String> message = MessageBuilder.withPayload("Hello Spring Integration 5").setHeader("key5", "value5")
				.build();
		Message<?> message1 = new MessagingTemplate().sendAndReceive(sendAndReceiveChannel, message);
		System.out.println(message1.getPayload());
	}

	private void printQueueChannel() throws InterruptedException, ExecutionException {
		List<Future<Message<String>>> futures = new ArrayList<Future<Message<String>>>();
		for (int i = 0; i < 10; i++) {
			Message<String> message = MessageBuilder.withPayload("Queue message number " + i)
					.setHeader("messageNumaber", Integer.toString(i)).build();
			futures.add(this.printGatewayQueue.print(message));
			System.out.println("Sending message number " + Integer.toString(i) + " >>>");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (Future<Message<String>> future : futures) {
			System.out.println(future.get().getPayload() + " <<<");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	private void printPriorityChannel() throws InterruptedException, ExecutionException {
		List<Future<Message<String>>> futures = new ArrayList<Future<Message<String>>>();
		for (int i = 0; i < 10; i++) {
			int r = (int) (Math.random() * 100);
			Message<String> message = MessageBuilder.withPayload("Queue message number " + r)
					.setHeader("messageNumaber", Integer.toString(r)).setPriority(r).build();
			futures.add(this.printGatewayPriority.print(message));
			System.out.println("Sending message number " + Integer.toString(r) + " >>>");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (Future<Message<String>> future : futures) {
			System.out.println(future.get().getPayload() + " <<<");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	private void printCompartorChannel() throws InterruptedException, ExecutionException {
		List<Future<Message<String>>> futures = new ArrayList<Future<Message<String>>>();
		for (int i = 0; i < 10; i++) {
			int r = (int) (Math.random() * 100);
			Message<String> message = MessageBuilder.withPayload("Queue message number " + r)
					.setHeader("messageNumaber", Integer.toString(r)).build();
			futures.add(this.printGatewayCompartor.print(message));
			System.out.println("Sending message number " + Integer.toString(r) + " >>>");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		for (Future<Message<String>> future : futures) {
			System.out.println(future.get().getPayload() + " <<<");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}

	private void printDirctChannel() {
		for (int i = 0; i < 10; i++) {
			System.out.print(i);
			Message<String> message = MessageBuilder.withPayload("Direct message number " + i).build();
			this.gatewayDirect.print(message);			
		}
	}
}
