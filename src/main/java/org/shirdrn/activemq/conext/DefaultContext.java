package org.shirdrn.activemq.conext;

import org.shirdrn.activemq.common.ActiveMQContext;

public class DefaultContext implements ActiveMQContext {

	private final ContextReadable config;
	
	public DefaultContext(ContextReadable context) {
		this.config = context;
	}

	@Override
	public ContextReadable getConfig() {
		return config;
	}
	
}
