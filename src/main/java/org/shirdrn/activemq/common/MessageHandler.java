package org.shirdrn.activemq.common;


public interface MessageHandler<M> {

	void handle(M message) throws MessageHandleException;
}
