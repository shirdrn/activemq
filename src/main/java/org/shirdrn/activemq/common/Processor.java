package org.shirdrn.activemq.common;

public interface Processor<IN, OUT> extends ActiveMQClient {

	void setMessageHandler(MessageHandler<IN, OUT> messageHandler);
	MessageHandler<IN, OUT> getMessageHandler();
	
}
