package com.zero.activeMQ;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class PublishExamples {
	public static void main(String[] args) throws JMSException, IOException {
		autoAcknowledge();
	}

	/**
	 * 自动应答模式，主题发布者
	 * 
	 * @throws JMSException
	 * @throws IOException
	 */
	public static void autoAcknowledge() throws JMSException, IOException {
		ConnectionFactory factory = new ActiveMQConnectionFactory();

		Connection connection = factory.createConnection();

		// connection.setClientID("ihiuhih12");
		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

		Topic topic = session.createTopic("topic");

		MessageProducer producer = session.createProducer(topic);

		for (int i = 0; i < 3; i++) {
			TextMessage message = session.createTextMessage("I am " + i + " auto_acknowledge message");

			producer.send(message);
		}
		producer.close();
		session.close();
		connection.close();
	}
}
