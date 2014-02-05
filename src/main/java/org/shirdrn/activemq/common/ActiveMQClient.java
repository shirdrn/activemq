package org.shirdrn.activemq.common;

import java.io.Closeable;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public interface ActiveMQClient extends Closeable {

	void establish() throws JMSException;
	
	String getUserName();
	String getPassword();
	String getBrokerURL();
	
	ActiveMQContext getContext();
	ConnectionFactory getConnectionFactory();
}
