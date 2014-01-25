package org.shirdrn.activemq.component;

import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.ActiveMQProducer;
import org.shirdrn.activemq.conext.ContextReadable;
import org.shirdrn.activemq.conext.DefaultActiveMQContext;
import org.shirdrn.activemq.conext.PropertiesConfiguration;

public class TestDefaultActiveMQProducer {

	private static final Log LOG = LogFactory.getLog(TestDefaultActiveMQProducer.class);
	ActiveMQContext context;
	ActiveMQProducer activeMQProducer;
	
	@Before
	public void initialize() {
		ContextReadable ctx = new PropertiesConfiguration("activemq.properties");
		context = new DefaultActiveMQContext(ctx);
		activeMQProducer = new DefaultActiveMQProducer(context);
	}
	
	@Test
	public void push() throws JMSException, InterruptedException {
		activeMQProducer.establish();
		for (int i = 950; i < 1000; i++) {
			String message = "xxxxxxxxxxx-" + i;
			activeMQProducer.push(message);
			LOG.info("Push message: " + message);
			Thread.sleep(100);
		}
	}
}
