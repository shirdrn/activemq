package org.shirdrn.activemq.producer;

import javax.jms.JMSException;
import javax.jms.Message;

import org.shirdrn.activemq.common.AbstractActiveMQMessageProcessor;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.ActiveMQProducer;

public abstract class AbstractActiveMQProducer<IN, OUT extends Message> extends AbstractActiveMQMessageProcessor<IN, OUT> implements ActiveMQProducer<IN, OUT> {

	public AbstractActiveMQProducer(ActiveMQContext context) {
		super(context);
	}
	
	@Override
	public void establish() throws JMSException {
		super.establish();
	}

}
