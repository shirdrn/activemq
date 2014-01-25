package org.shirdrn.activemq.common;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.shirdrn.activemq.utils.ReflectionUtils;

public abstract class AbstractActiveMQClient implements ActiveMQClient {
	
	protected final ActiveMQContext context;
	protected final ConnectionManager connectionManager;
//	private final SessionManager sessionManager;
	private final String userName;
	private final String password;
	private final String brokerURL;
	private final ConnectionFactory connectionFactory;
	
	public AbstractActiveMQClient(ActiveMQContext context) {
		this.context = context;
		userName = context.get("activemq.username", ActiveMQConnection.DEFAULT_USER);
		password = context.get("activemq.password", ActiveMQConnection.DEFAULT_PASSWORD);
		brokerURL = context.get("activemq.broker.url", "tcp://localhost:61616");
		String cmClass = context.get("activemq.connection.manager.class", "org.shirdrn.activemq.component.cm.SingleConnectionManager");
		connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connectionManager = ReflectionUtils.getInstance(cmClass, ConnectionManager.class, this.getClass().getClassLoader(), this);
	}
	
	@Override
	public ActiveMQContext getContext() {
		return context;
	}

	@Override
	public void close() throws IOException {
		connectionManager.close();		
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getBrokerURL() {
		return brokerURL;
	}

	@Override
	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	@Override
	public void establish() throws JMSException {
		Connection connection = connectionManager.getConnection();
		connection.start();
	}
	
}
