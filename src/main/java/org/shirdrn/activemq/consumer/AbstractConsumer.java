package org.shirdrn.activemq.consumer;

import javax.jms.JMSException;
import javax.jms.Message;

import org.shirdrn.activemq.common.AbstractMessageProcessor;
import org.shirdrn.activemq.common.Consumer;
import org.shirdrn.activemq.common.ActiveMQContext;

public abstract class AbstractConsumer<IN extends Message, OUT> extends AbstractMessageProcessor<IN, OUT> implements Consumer<IN, OUT> {

	public AbstractConsumer(ActiveMQContext context) {
		super(context);
	}
	
	@Override
	public void establish() throws JMSException {
		super.establish();
	}
	
}
