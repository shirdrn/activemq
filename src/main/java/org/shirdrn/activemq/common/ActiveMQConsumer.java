package org.shirdrn.activemq.common;

import javax.jms.Message;

public interface ActiveMQConsumer extends ActiveMQClient {

	Message pull();
	
}
