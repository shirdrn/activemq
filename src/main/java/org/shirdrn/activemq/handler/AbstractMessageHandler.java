package org.shirdrn.activemq.handler;

import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.MessageHandler;

public abstract class AbstractMessageHandler<IN, OUT> implements MessageHandler<IN, OUT> {

	protected final ActiveMQContext context;
	
	public AbstractMessageHandler(ActiveMQContext context) {
		this.context = context;
	}
}
