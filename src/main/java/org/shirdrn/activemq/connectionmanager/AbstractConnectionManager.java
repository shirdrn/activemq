package org.shirdrn.activemq.connectionmanager;

import java.io.IOException;

import javax.jms.ConnectionFactory;

import org.shirdrn.activemq.common.ActiveMQClient;
import org.shirdrn.activemq.common.ConnectionManager;
import org.shirdrn.activemq.common.SessionManager;
import org.shirdrn.activemq.utils.CheckUtils;
import org.shirdrn.activemq.utils.ReflectionUtils;

public abstract class AbstractConnectionManager implements ConnectionManager {

	protected final ConnectionFactory connectionFactory;
	protected final SessionManager sessionManager;
	protected final ActiveMQClient activeMQClient;

	public AbstractConnectionManager(ActiveMQClient activeMQClient) {
		this.activeMQClient = activeMQClient;
		this.connectionFactory = activeMQClient.getConnectionFactory();
		String smClass = activeMQClient.getContext().getConfig().get("activemq.session.manager.class");
		CheckUtils.checkNotNull(smClass, "activemq.session.manager.class is NOT set!");
		sessionManager = ReflectionUtils.getInstance(smClass, SessionManager.class, this.getClass().getClassLoader(), this);
	}
	
	@Override
	public void close() throws IOException {
		sessionManager.close();
	}

	@Override
	public SessionManager getSessionManager() {
		return sessionManager;
	}

	@Override
	public ActiveMQClient getClient() {
		return activeMQClient;
	}

}
