package com.zero.activeMQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
	private final static Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@JmsListener(destination = "queue-mode", containerFactory = "queueListener")
	@SendTo("SQueue")
	public String handleMessage(String name) {
		logger.info("success receive name : {}", name);
		return name;
	}

	@JmsListener(destination = "topic-mode", containerFactory = "topicListener")
	public String topicMessage(String name) {
		logger.info("success receive name: {}", name);
		return name;
	}
}
