package org.shirdrn.activemq.sessionmanager;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.shirdrn.activemq.common.ConnectionManager;

public class TopicSessionManager extends QueueSessionManager {

	public TopicSessionManager(ConnectionManager connectionManager) {
		super(connectionManager);
	}
	
	@Override
	protected Destination newDestination(Session session) {
		Destination destination = null;
		try {
			String topicName = context.getConfig().get("activemq.topic.name");
			destination = session.createTopic(topicName);
		} catch (JMSException e) {
			throw new RuntimeException(e); 
		}
		return destination;
	}

}
