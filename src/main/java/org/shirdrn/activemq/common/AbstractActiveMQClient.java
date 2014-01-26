package org.shirdrn.activemq.common;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.utils.ReflectionUtils;

public abstract class AbstractActiveMQClient<M> implements ActiveMQClient, MessageHandlerConfigurable<M> {
	
	private static final Log LOG = LogFactory.getLog(AbstractActiveMQClient.class);
	protected final ActiveMQContext context;
	protected final ConnectionManager connectionManager;
	private final String userName;
	private final String password;
	private final String brokerURL;
	private final ConnectionFactory connectionFactory;
	
	protected MessageHandler<M> messageHandler;
	private final ExecutorFactory executorFactory;
	private final ActiveMQExecutor executor;
	
	@SuppressWarnings("unchecked")
	public AbstractActiveMQClient(ActiveMQContext context) {
		this.context = context;
		userName = context.get("activemq.username", ActiveMQConnection.DEFAULT_USER);
		password = context.get("activemq.password", ActiveMQConnection.DEFAULT_PASSWORD);
		brokerURL = context.get("activemq.broker.url", "tcp://localhost:61616");
		String cmClass = context.get("activemq.connection.manager.class", "org.shirdrn.activemq.connectionmanager.DefaultConnectionManager");
		connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerURL);
		connectionManager = ReflectionUtils.getInstance(cmClass, ConnectionManager.class, this.getClass().getClassLoader(), this);
		
		String handlerClass = context.get("activemq.handler.class");
		if(handlerClass != null) {
			messageHandler = ReflectionUtils.getInstance(handlerClass, MessageHandler.class, this.getClass().getClassLoader(), context);
		}
		String handlerExecutorFactoryClass = context.get("activemq.handler.executor.factory.class", "org.shirdrn.activemq.executor.DefaultExecutorFactory");
		executorFactory = ReflectionUtils.getInstance(handlerExecutorFactoryClass, ExecutorFactory.class, this.getClass().getClassLoader());
		executor = executorFactory.get(context);
	}
	
	@Override
	public void establish() throws JMSException {
		Connection connection = connectionManager.getConnection();
		connection.start();
	}
	
	protected Future<Result<M>> submit(final M message) {
		return executor.submit(new Callable<Result<M>>() {

			@Override
			public Result<M> call() throws Exception {
				Result<M> result = new DefaultResult<M>();
				try {
					result.setMessage(message);
					messageHandler.handle(message);
				} catch (MessageHandleException e) {
					result.addException(e);
					LOG.error("Fail to handle message: " + e);
				} catch (Exception e) {
					result.addException(e);
					LOG.error("Fail to handle message: " + e);
				}
				return result;
			}
			
		});
	}
	
	protected void execute(final M message) {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					messageHandler.handle(message);
				} catch (MessageHandleException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
			
		});
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
	public MessageHandler<M> getMessageHandler() {
		return messageHandler;
	}

	@Override
	public void setMessageHandler(MessageHandler<M> messageHandler) {
		this.messageHandler = messageHandler;
	}

}
