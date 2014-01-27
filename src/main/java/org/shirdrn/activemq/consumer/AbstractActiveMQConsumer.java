package org.shirdrn.activemq.consumer;

import javax.jms.JMSException;
import javax.jms.Message;

import org.shirdrn.activemq.common.AbstractActiveMQMessageProcessor;
import org.shirdrn.activemq.common.ActiveMQConsumer;
import org.shirdrn.activemq.common.ActiveMQContext;

public abstract class AbstractActiveMQConsumer<IN extends Message, OUT> extends AbstractActiveMQMessageProcessor<IN, OUT> implements ActiveMQConsumer<IN, OUT> {

	public AbstractActiveMQConsumer(ActiveMQContext context) {
		super(context);
	}
	
	@Override
	public void establish() throws JMSException {
		super.establish();
	}
	
}
