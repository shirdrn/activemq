package org.shirdrn.activemq.common;

import java.io.Closeable;

import javax.jms.Connection;

public interface ConnectionManager extends Closeable {

	Connection getConnection();
	SessionManager getSessionManager();
	ActiveMQClient getActiveMQClient();
	
}
