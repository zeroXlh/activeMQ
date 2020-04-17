package com.zero.activeMQ.beanconfig;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

@Configuration
public class BeanConfig {

	@Bean
	public Destination queue() {
		return new ActiveMQQueue("ActiveMQQueue");
	}

	@Value("${spring.activemq.broker-url}")
	private String brokerURL;

	@Bean
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, brokerURL);
	}

	@Bean
	public JmsMessagingTemplate jmsMessagingTemplate(ConnectionFactory connectionFactory) {
		return new JmsMessagingTemplate(connectionFactory);
	}

	@Bean("queueListener")
	public JmsListenerContainerFactory<SimpleMessageListenerContainer> queueJmsListenerContainerFactory1(
			ConnectionFactory connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setPubSubDomain(false);
		return factory;
	}

	@Bean("topicListener")
	public JmsListenerContainerFactory<SimpleMessageListenerContainer> topicJmsListenerContainerFactory1(
			ConnectionFactory connectionFactory) {
		SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setPubSubDomain(true);
		return factory;
	}
}
