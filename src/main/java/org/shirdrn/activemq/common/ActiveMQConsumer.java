package org.shirdrn.activemq.common;

import javax.jms.Message;

public interface ActiveMQConsumer<IN extends Message, OUT> extends ActiveMQClient {

	Result<OUT> consume();
	void setMessageHandler(MessageHandler<IN, OUT> messageHandler);
	MessageHandler<IN, OUT> getMessageHandler();
	
}
