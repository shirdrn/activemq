package org.shirdrn.activemq.producer;

import javax.jms.JMSException;
import javax.jms.StreamMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.ActiveMQContext;

public class ActiveMQStreamProducer extends AbstractSimpleActiveMQProducer<byte[]> {

	private static final Log LOG = LogFactory.getLog(ActiveMQStreamProducer.class);
	
	public ActiveMQStreamProducer(ActiveMQContext context) {
		super(context);
	}

	@Override
	public void produce(byte[] message) {
		try {
			StreamMessage m = session.createStreamMessage();
			m.writeBytes(message);
			messageProducer.send(m);
		} catch (JMSException e) {
			LOG.error("Fail to produce message: " + e);
		}
	}

}
