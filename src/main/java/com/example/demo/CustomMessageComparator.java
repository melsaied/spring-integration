package com.example.demo;

import java.util.Comparator;

import org.springframework.messaging.Message;

public class CustomMessageComparator implements Comparator<Message<String>> {

	@Override
	public int compare(Message<String> o1, Message<String> o2) {
		String payload1 = o1.getPayload();
		String payload2 = o2.getPayload();
		boolean payload1Even = Integer.valueOf(payload1.substring(payload1.length() - 1)) % 2 == 0;
		boolean payload2Even = Integer.valueOf(payload2.substring(payload2.length() - 1)) % 2 == 0;
		if ((payload1Even && payload2Even) || (!payload1Even && !payload2Even)) {
			return 0;
		} else if (payload1Even) {
			return -1;
		} else {
			return 1;
		}
	}
}
