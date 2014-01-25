package org.shirdrn.activemq.component;

import java.io.IOException;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.AbstractActiveMQProducer;
import org.shirdrn.activemq.common.ActiveMQContext;

public class DefaultActiveMQProducer extends AbstractActiveMQProducer {

	private static final Log LOG = LogFactory.getLog(DefaultActiveMQProducer.class);
	protected Session session;
	protected Destination destination;
	protected MessageProducer messageProducer;
	
	public DefaultActiveMQProducer(ActiveMQContext context) {
		super(context);
	}

	@Override
	public void establish() throws JMSException {
		super.establish();
		session = connectionManager.getSessionManager().getSession();
		destination = connectionManager.getSessionManager().getDestination(session);
		messageProducer = session.createProducer(destination);
		int deliveryMode = context.getInt("activemq.delivery.mode", DeliveryMode.NON_PERSISTENT);
		messageProducer.setDeliveryMode(deliveryMode);
	}

	@Override
	public void close() throws IOException {
		super.close();
	}

	@Override
	public void push(String message) {
		try {
			TextMessage textMessage = session.createTextMessage(message);
			messageProducer.send(destination, textMessage);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
