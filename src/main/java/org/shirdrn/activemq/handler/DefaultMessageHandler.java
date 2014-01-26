package org.shirdrn.activemq.handler;

import javax.jms.Message;

import org.shirdrn.activemq.common.MessageHandleException;
import org.shirdrn.activemq.common.MessageHandler;

public class DefaultMessageHandler implements MessageHandler<Message> {

	@Override
	public void handle(Message message) throws MessageHandleException {
		
	}

}
