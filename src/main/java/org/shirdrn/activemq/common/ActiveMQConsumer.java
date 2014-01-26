package org.shirdrn.activemq.common;


public interface ActiveMQConsumer<M> extends ActiveMQClient, MessageHandlerConfigurable<M> {

	Result<M> consume();
	
}
