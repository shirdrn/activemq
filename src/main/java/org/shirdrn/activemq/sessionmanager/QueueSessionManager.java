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

public class QueueSessionManager extends AbstractSessionManager {

	protected ConcurrentMap<Session, Destination> sessions;
	
	public QueueSessionManager(ConnectionManager connectionManager) {
		super(connectionManager);
		sessions = new ConcurrentHashMap<Session, Destination>(1);
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
			Destination destination = newDestination(session);
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

	@Override
	protected Destination newDestination(Session session) {
		Destination destination = null;
		try {
			String queueName = context.getConfig().get("activemq.queue.name");
			destination = session.createQueue(queueName);
		} catch (JMSException e) {
			throw new RuntimeException(e); 
		}
		return destination;
	}

}
