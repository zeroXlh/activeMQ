package com.zero.activeMQ;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class PointConsumerDemo {
	public static void main(String[] args) throws JMSException {
		// 启用事务消费
		// transacted();

		// autoAcknowledge();

		clientAcknowledge();
	}

	/**
	 * 客户端应答
	 * 
	 * @throws JMSException
	 */
	private static void clientAcknowledge() throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");

		Connection connection = factory.createConnection();
		connection.start();

		// 不启动事务
		Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);

		Destination destination = session.createQueue("client_queue");

		MessageConsumer consumer = session.createConsumer(destination);

		while (true) {
			// 启用超时方法消费消息，如果使用receive()方法且队列为空（即没有消息可以消费）时，将会阻塞
			TextMessage textMessage = (TextMessage) consumer.receive((long) 2000);
			// 消费端如果不应答，队列消息将不会删除（即认为未被消费）

			if (null == textMessage)
				break;

			textMessage.acknowledge();
			System.out.println(textMessage.getText());
		}
		consumer.close();
		session.close();
		connection.close();
	}

	@SuppressWarnings("unused")
	private static void autoAcknowledge() throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");

		Connection connection = factory.createConnection();
		connection.start();

		// 不启动事务
		Session session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

		Destination destination = session.createQueue("aotu_queue");

		MessageConsumer consumer = session.createConsumer(destination);

		while (true) {
			// 启用超时方法消费消息，如果使用receive()方法且队列为空（即没有消息可以消费）时，将会阻塞
			TextMessage textMessage = (TextMessage) consumer.receive((long) 2000);

			if (null == textMessage)
				break;

			System.out.println(textMessage.getText());
		}
		consumer.close();
		session.close();
		connection.close();
	}

	@SuppressWarnings("unused")
	private static void transacted() throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");

		Connection connection = factory.createConnection();
		connection.start();

		// 要启动事务，只要设置第一个参数为true即可，应答模式
		Session session = connection.createSession(Boolean.TRUE, Session.SESSION_TRANSACTED);

		Destination destination = session.createQueue("queue");

		MessageConsumer consumer = session.createConsumer(destination);

		while (true) {
			TextMessage textMessage = (TextMessage) consumer.receive((long) 2000);
			// 消费端如果不提交，队列消息将不会删除（即认为未被消费）
			session.commit();
			if (null == textMessage)
				break;

			System.out.println(textMessage.getText());
		}
		consumer.close();
		session.close();
		connection.close();
	}
}
