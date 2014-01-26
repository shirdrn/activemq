package org.shirdrn.activemq.sessionmanager;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.shirdrn.activemq.common.ConnectionManager;

public class DefaultSessionManager extends AbstractSessionManager {

	protected ConcurrentMap<Session, Destination> sessions;
	
	public DefaultSessionManager(ConnectionManager connectionManager) {
		super(connectionManager);
		sessions = new ConcurrentHashMap<Session, Destination>();
	}

	@Override
	public void close() throws IOException {
		Iterator<Entry<Session, Destination>> iter = sessions.entrySet().iterator();
		while(iter.hasNext()) {
			try {
				iter.next().getKey().close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
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
