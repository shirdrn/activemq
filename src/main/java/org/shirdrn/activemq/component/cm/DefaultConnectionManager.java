package org.shirdrn.activemq.component.cm;

import javax.jms.Connection;
import javax.jms.JMSException;

import org.shirdrn.activemq.common.AbstractConnectionManager;
import org.shirdrn.activemq.common.ActiveMQClient;

public class DefaultConnectionManager extends AbstractConnectionManager {

	private final Connection connection;
	
	public DefaultConnectionManager(ActiveMQClient activeMQClient) throws JMSException {
		super(activeMQClient);
		String userName = activeMQClient.getUserName();
		String password = activeMQClient.getPassword();
		connection = activeMQClient.getConnectionFactory().createConnection(userName, password);
	}

	@Override
	public Connection getConnection() {
		return connection;
	}

}
