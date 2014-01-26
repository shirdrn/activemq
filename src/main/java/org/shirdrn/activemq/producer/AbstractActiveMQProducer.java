package org.shirdrn.activemq.producer;

import org.shirdrn.activemq.common.AbstractActiveMQClient;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.ActiveMQProducer;


public abstract class AbstractActiveMQProducer<M> extends AbstractActiveMQClient<M> implements ActiveMQProducer<M> {

	public AbstractActiveMQProducer(ActiveMQContext context) {
		super(context);
		
	}

}
