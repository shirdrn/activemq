package org.shirdrn.activemq.conext;

import org.shirdrn.activemq.common.ActiveMQContext;

public class DefaultActiveMQContext implements ActiveMQContext {

	private final ContextReadable config;
	
	public DefaultActiveMQContext(ContextReadable context) {
		this.config = context;
	}

	@Override
	public ContextReadable getConfig() {
		return config;
	}
	
}
