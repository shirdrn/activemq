package org.shirdrn.activemq.producer;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.ActiveMQContext;

public class ActiveMQTextProducer extends AbstractSimpleActiveMQProducer<String> {

	private static final Log LOG = LogFactory.getLog(ActiveMQTextProducer.class);
	
	public ActiveMQTextProducer(ActiveMQContext context) {
		super(context);
	}

	@Override
	public void produce(String message) {
		try {
			super.submit(message);
			messageProducer.send(m);
		} catch (JMSException e) {
			LOG.error("Fail to produce message: " + e);
		}
	}

}
