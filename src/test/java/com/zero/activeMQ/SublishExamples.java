package com.zero.activeMQ;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
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
		autoAcknowledge();
	}

	public static void durableAndAutoAcknowledge() throws IOException, JMSException {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = factory.createConnection();

		// 必须指定clientID，activeMQ才能识别持久订阅者（不指定由activeMQ随机指定）
		connection.setClientID("durableSubscriber1");
		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("topic");

		// 指定订阅的主题和订阅者的名称
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

	/**
	 * 自动应答模式，非持久订阅者
	 * 
	 * @throws IOException
	 * @throws JMSException
	 */
	public static void autoAcknowledge() throws IOException, JMSException {
		ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = factory.createConnection();

		connection.start();
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("topic");

		MessageConsumer consumer = session.createConsumer(topic);

		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				String text;
				try {
					text = textMessage.getText();

					System.out.println("receive mesage: " + text + ", messageId：" + textMessage.getJMSMessageID());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

		consumer.close();
		session.close();
		connection.close();
	}
}
