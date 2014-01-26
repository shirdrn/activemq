package org.shirdrn.activemq.consumer;

import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.shirdrn.activemq.common.ActiveMQConsumer;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.Result;
import org.shirdrn.activemq.conext.ContextReadable;
import org.shirdrn.activemq.conext.DefaultActiveMQContext;
import org.shirdrn.activemq.conext.PropertiesConfiguration;
import org.shirdrn.activemq.consumer.ActiveMQTextConsumer;

public class TestActiveMQTextConsumer {

	private static final Log LOG = LogFactory.getLog(TestActiveMQTextConsumer.class);
	ActiveMQContext context;
	ActiveMQConsumer<String> consumer;
	
	@Before
	public void initialize() {
		ContextReadable ctx = new PropertiesConfiguration("activemq.properties");
		context = new DefaultActiveMQContext(ctx);
		consumer = new ActiveMQTextConsumer(context);
	}
	
	@Test
	public void pull() throws JMSException, InterruptedException {
		consumer.establish();
		while(true) {
			Result<String> result = consumer.consume();
			LOG.info("Pull message: " + result.getMessage());
		}
	}
}
