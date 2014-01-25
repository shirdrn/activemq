package org.shirdrn.activemq.common;

import javax.jms.MessageListener;

public abstract class AbstractMessageListener implements MessageListener {

	protected final ActiveMQContext context;
	
	public AbstractMessageListener(ActiveMQContext context) {
		this.context = context;
	}
}
