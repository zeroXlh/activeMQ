package com.zero.activeMQ;

import javax.annotation.PostConstruct;
import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@Autowired
	private Destination queue;
	@PostConstruct
	public void send() {
		System.out.println("发送数据");
		jmsMessagingTemplate.convertAndSend(queue, "Hello World!");
	}
	
}
