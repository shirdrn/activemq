package org.shirdrn.activemq.connectionmanager;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;

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

	@Override
	public void close() throws IOException {
		super.close();
		try {
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
