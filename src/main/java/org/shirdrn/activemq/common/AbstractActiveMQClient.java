package org.shirdrn.activemq.common;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.utils.ReflectionUtils;

public abstract class AbstractActiveMQClient implements ActiveMQClient {
	
	private static final Log LOG = LogFactory.getLog(AbstractActiveMQClient.class);
	protected final ActiveMQContext context;
	protected final ConnectionManager connectionManager;
	private final String userName;
	private final String password;
	private final String brokerURL;
	private final ConnectionFactory connectionFactory;
	
	private final ExecutorFactory executorFactory;
	protected final ActiveMQExecutor executor;
	
	public AbstractActiveMQClient(ActiveMQContext context) {
		this.context = context;
		userName = context.getConfig().get("activemq.username", ActiveMQConnection.DEFAULT_USER);
		password = context.getConfig().get("activemq.password", ActiveMQConnection.DEFAULT_PASSWORD);
		brokerURL = context.getConfig().get("activemq.broker.url", "tcp://localhost:61616");
		LOG.info("Client config: brokerURL = " + brokerURL);
		
		String cmClass = context.getConfig().get("activemq.connection.manager.class", "org.shirdrn.activemq.connectionmanager.DefaultConnectionManager");
		LOG.info("Client config: connection.manager.class = " + cmClass);
		connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connectionManager = ReflectionUtils.getInstance(cmClass, ConnectionManager.class, this.getClass().getClassLoader(), this);
		
		String handlerExecutorFactoryClass = context.getConfig().get("activemq.executor.factory.class", "org.shirdrn.activemq.executor.DefaultExecutorFactory");
		LOG.info("Client config: executor.factory.class = " + handlerExecutorFactoryClass);
		executorFactory = ReflectionUtils.getInstance(handlerExecutorFactoryClass, ExecutorFactory.class, this.getClass().getClassLoader());
		executor = executorFactory.get(context.getConfig());
	}
	
	@Override
	public void establish() throws JMSException {
		Connection connection = connectionManager.getConnection();
		connection.start();
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
	
}
