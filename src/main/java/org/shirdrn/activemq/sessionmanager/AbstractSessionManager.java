package org.shirdrn.activemq.sessionmanager;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.ConnectionManager;
import org.shirdrn.activemq.common.SessionManager;

public abstract class AbstractSessionManager implements SessionManager {

	protected final ActiveMQContext context;
	protected final ConnectionManager connectionManager;
	
	public AbstractSessionManager(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
		context = connectionManager.getClient().getContext();
	}
	
	protected Session newSession() throws JMSException {
		boolean transacted = context.getConfig().getBoolean("activemq.session.transacted", false);
		int acknowledgeMode = context.getConfig().getInt("activemq.session.transacted", Session.AUTO_ACKNOWLEDGE);
		Connection connection = connectionManager.getConnection();
		return connection.createSession(transacted, acknowledgeMode);
	}
	
	protected abstract Destination newDestination(Session session);
	
}
