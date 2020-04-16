package com.zero.activeMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class PublishExamples {
	public static void main(String[] args) throws JMSException {
		ConnectionFactory factory = new ActiveMQConnectionFactory();

		// Object createConnection;
		Connection connection = factory.createConnection();

		connection.start();

		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("topic");

		MessageProducer producer = session.createProducer(topic);

		for (int i = 0; i < 3; i++) {
			TextMessage message = session.createTextMessage("I am " + i + " message");

			producer.send(message);
		}

		producer.close();
		session.close();
		connection.close();
	}
}
