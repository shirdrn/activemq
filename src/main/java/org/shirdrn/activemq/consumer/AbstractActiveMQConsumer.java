package org.shirdrn.activemq.consumer;

import org.shirdrn.activemq.common.AbstractActiveMQClient;
import org.shirdrn.activemq.common.ActiveMQConsumer;
import org.shirdrn.activemq.common.ActiveMQContext;


public abstract class AbstractActiveMQConsumer<M> extends AbstractActiveMQClient<M> implements ActiveMQConsumer<M> {

	public AbstractActiveMQConsumer(ActiveMQContext context) {
		super(context);
	}
	
}
