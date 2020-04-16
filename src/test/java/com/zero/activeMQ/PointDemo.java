package com.zero.activeMQ;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class PointDemo {

	public static void main(String[] args) throws JMSException {

		// 启动事务、非持久化消息
		// nonPersistentAndTransacted();

		 autoAcknowledge();

//		 clientAcknowledge();
	}

	/**
	 * 客户端应答模式，非持久化，非事务
	 * 
	 * @throws JMSException
	 */
	private static void clientAcknowledge() throws JMSException {
		ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
		Connection connection = factory.createConnection();

		connection.start();

		// 不启动事务，客户端应答
		Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
		Destination destination = session.createQueue("client_queue");
		MessageProducer producer = session.createProducer(destination);

		// 非持久化
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		for (int i = 0; i <= 5; i++) {
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText("I am " + i + "message");
			producer.send(textMessage);
		}
		if (connection != null)
			connection.close();
	}

	/**
	 * 自动应答模式，非持久化，非事务
	 * 
	 * @throws JMSException
	 */
	@SuppressWarnings("unused")
	private static void autoAcknowledge() throws JMSException {
		ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
		Connection connection = factory.createConnection();

		connection.start();

		// 不启动事务，自动应答
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("queue-mode");
		MessageProducer producer = session.createProducer(destination);

		// 非持久化
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		for (int i = 0; i <= 5; i++) {
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText("I am " + i + " message");
			producer.send(textMessage);
		}
		if (connection != null)
			connection.close();
	}

	@SuppressWarnings("unused")
	private static void nonPersistentAndTransacted() throws JMSException {
		ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
		Connection connection = factory.createConnection();

		connection.start();

		// 启动事务提交，事务提交
		Session session = connection.createSession(Boolean.TRUE, Session.SESSION_TRANSACTED);
		Destination destination = session.createQueue("queue");
		MessageProducer producer = session.createProducer(destination);

		// 非持久化
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		for (int i = 0; i <= 5; i++) {
			TextMessage textMessage = session.createTextMessage();
			textMessage.setText("I am " + i + "message");
			producer.send(textMessage);
			// 生产端如果不提交，则无法加入到队列
			session.commit();
		}
		if (connection != null)
			connection.close();
	}

}
