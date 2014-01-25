package org.shirdrn.activemq.common;

import java.io.Closeable;

import javax.jms.Destination;
import javax.jms.Session;

public interface SessionManager extends Closeable {

	Session getSession();
	Destination getDestination(Session session);
	
}
