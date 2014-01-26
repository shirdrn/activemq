package org.shirdrn.activemq.common;


public interface ActiveMQProducer<M> extends ActiveMQClient, MessageHandlerConfigurable<M> {

	void produce(M message);
}
