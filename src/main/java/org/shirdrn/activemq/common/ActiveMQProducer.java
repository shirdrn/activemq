package org.shirdrn.activemq.common;


public interface ActiveMQProducer extends ActiveMQClient {

	void push(String message);
}
