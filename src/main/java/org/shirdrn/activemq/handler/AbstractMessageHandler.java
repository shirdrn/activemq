package org.shirdrn.activemq.handler;

import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.MessageHandler;

public abstract class AbstractMessageHandler<M> implements MessageHandler<M> {

	protected final ActiveMQContext context;
	
	public AbstractMessageHandler(ActiveMQContext context) {
		this.context = context;
	}
}
