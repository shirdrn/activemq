package org.shirdrn.activemq.common;

import javax.jms.Message;

public interface ActiveMQProducer<IN, OUT extends Message> extends ActiveMQClient {

	Result<OUT> produce(IN message);
	void setMessageHandler(MessageHandler<IN, OUT> messageHandler);
	MessageHandler<IN, OUT> getMessageHandler();
}
