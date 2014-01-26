package org.shirdrn.activemq.common;


public interface MessageHandlerConfigurable<M> {

	void setMessageHandler(MessageHandler<M> messageHandler);
	MessageHandler<M> getMessageHandler();
}
