package org.shirdrn.activemq.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.ActiveMQContext;

public class TextMessageHandler extends AbstractMessageHandler<String> {

	private static final Log LOG = LogFactory.getLog(TextMessageHandler.class);
	
	public TextMessageHandler(ActiveMQContext context) {
		super(context);
	}

	@Override
	public void handle(String message) {
		LOG.info(message);
	}

}
