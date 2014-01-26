package org.shirdrn.activemq.producer;

import javax.jms.JMSException;
import javax.jms.Message;

import org.shirdrn.activemq.common.ActiveMQContext;

public class DefaultActiveMQProducer extends AbstractSimpleActiveMQProducer<Message> {

	public DefaultActiveMQProducer(ActiveMQContext context) {
		super(context);
	}

	@Override
	public void produce(Message message) {
		try {
			super.messageProducer.send(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}		
	}

}
