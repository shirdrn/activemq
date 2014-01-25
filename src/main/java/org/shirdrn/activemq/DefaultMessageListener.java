package org.shirdrn.activemq;

import javax.jms.Message;

import org.shirdrn.activemq.common.AbstractMessageListener;
import org.shirdrn.activemq.common.ActiveMQContext;

public class DefaultMessageListener extends AbstractMessageListener {

	public DefaultMessageListener(ActiveMQContext context) {
		super(context);
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

	}

}
