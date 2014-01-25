package org.shirdrn.activemq.component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.AbstractActiveMQConsumer;
import org.shirdrn.activemq.common.ActiveMQContext;

public class DefaultActiveMQConsumer extends AbstractActiveMQConsumer {

	private static final Log LOG = LogFactory.getLog(DefaultActiveMQConsumer.class);
	protected Session session;
	protected Destination destination;
	protected MessageConsumer messageConsumer;
	
	public DefaultActiveMQConsumer(ActiveMQContext context) {
		super(context);
	}

	@Override
	public void establish() throws JMSException {
		super.establish();
		session = connectionManager.getSessionManager().getSession();
		destination = connectionManager.getSessionManager().getDestination(session);
		messageConsumer = session.createConsumer(destination);
	}
	
	@Override
	public Message pull() {
		Message message = null;
		try {
			message = messageConsumer.receive();
			if(message != null) {
				final Message rawMessage = message;
				executor.execute(new Runnable() {
					@Override
					public void run() {
						messageListener.onMessage(rawMessage);					
					}
				});
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return message;
	}

}
