package org.shirdrn.activemq.consumer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.ActiveMQContext;

public abstract class AbstractSimpleActiveMQConsumer<M> extends AbstractActiveMQConsumer<M> {

	private static final Log LOG = LogFactory.getLog(AbstractSimpleActiveMQConsumer.class);
	protected Session session;
	protected Destination destination;
	protected MessageConsumer messageConsumer;
	
	public AbstractSimpleActiveMQConsumer(ActiveMQContext context) {
		super(context);
	}

	@Override
	public void establish() throws JMSException {
		super.establish();
		session = connectionManager.getSessionManager().getSession();
		destination = connectionManager.getSessionManager().getDestination(session);
		messageConsumer = session.createConsumer(destination);
	}
	
}
