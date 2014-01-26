package org.shirdrn.activemq.producer;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.ActiveMQContext;

public abstract class AbstractSimpleActiveMQProducer<M> extends AbstractActiveMQProducer<M> {

	private static final Log LOG = LogFactory.getLog(AbstractSimpleActiveMQProducer.class);
	protected Session session;
	protected MessageProducer messageProducer;
	private Destination destination;
	
	public AbstractSimpleActiveMQProducer(ActiveMQContext context) {
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
		LOG.info("Config: deliveryMode = " + deliveryMode);
	}

}
