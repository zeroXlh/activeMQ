package com.zero.activeMQ;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SublishExamples {
	public static void main(String[] args) throws JMSException, IOException {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = factory.createConnection("subscriber1", "123");

		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("topic");

		TopicSubscriber consumer = session.createDurableSubscriber(topic, "subscriber1");

		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				String text;
				try {
					text = textMessage.getText();

					System.out.println("receive mesage: " + text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		System.out.println("consumer already start！");

		System.in.read();
		consumer.close();
		session.close();
		connection.close();
	}
}
