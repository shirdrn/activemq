package org.shirdrn.activemq.common;


public abstract class AbstractActiveMQProducer extends AbstractActiveMQClient implements ActiveMQProducer {

	public AbstractActiveMQProducer(ActiveMQContext context) {
		super(context);
	}

}
