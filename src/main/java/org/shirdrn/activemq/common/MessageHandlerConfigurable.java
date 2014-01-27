package org.shirdrn.activemq.common;

public interface MessageHandlerConfigurable<IN, OUT> {

	void setMessageHandler(MessageHandler<IN, OUT> messageHandler);
	MessageHandler<IN, OUT> getMessageHandler();
}
