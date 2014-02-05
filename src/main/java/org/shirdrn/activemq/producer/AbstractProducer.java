package org.shirdrn.activemq.producer;

import javax.jms.JMSException;
import javax.jms.Message;

import org.shirdrn.activemq.common.AbstractMessageProcessor;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.Producer;

public abstract class AbstractProducer<IN, OUT extends Message> extends AbstractMessageProcessor<IN, OUT> implements Producer<IN, OUT> {

	public AbstractProducer(ActiveMQContext context) {
		super(context);
	}
	
	@Override
	public void establish() throws JMSException {
		super.establish();
	}

}
