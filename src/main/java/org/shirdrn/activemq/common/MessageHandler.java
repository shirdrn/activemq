package org.shirdrn.activemq.common;

import javax.jms.JMSException;

public interface MessageHandler<IN, OUT> {

	OUT handle(IN message) throws JMSException;
}
