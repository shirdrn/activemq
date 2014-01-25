package org.shirdrn.activemq.common;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

public abstract class AbstractSessionManager implements SessionManager {

	protected final ActiveMQContext context;
	protected final ConnectionManager connectionManager;
	
	public AbstractSessionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
		context = connectionManager.getActiveMQClient().getContext();
	}
	
	protected Session newSession() throws JMSException {
		boolean transacted = context.getBoolean("activemq.session.transacted", false);
		int acknowledgeMode = context.getInt("activemq.session.transacted", Session.AUTO_ACKNOWLEDGE);
		Connection connection = connectionManager.getConnection();
		return connection.createSession(transacted, acknowledgeMode);
	}
	
	@Override
	public void close() throws IOException {
		connectionManager.close();
	}

}
