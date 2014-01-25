package org.shirdrn.activemq.component.sm;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.shirdrn.activemq.common.AbstractSessionManager;
import org.shirdrn.activemq.common.ConnectionManager;

public class DefaultSessionManager extends AbstractSessionManager {

	protected ConcurrentMap<Session, Destination> sessions;
	
	public DefaultSessionManager(ConnectionManager connectionManager) {
		super(connectionManager);
		sessions = new ConcurrentHashMap<Session, Destination>();
	}

	@Override
	public void close() throws IOException {
		super.close();
	}
	
	@Override
	public Session getSession() {
		Session session = null;
		try {
			session = super.newSession();
			String queueName = context.get("activemq.queue.name");
			Destination destination = session.createQueue(queueName);
			sessions.putIfAbsent(session, destination);
		} catch (JMSException e) {
			throw new RuntimeException(e); 
		}
		return session;
	}

	@Override
	public Destination getDestination(Session session) {
		return sessions.get(session);
	}

}
