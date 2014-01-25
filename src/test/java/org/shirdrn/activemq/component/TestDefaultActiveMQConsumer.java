package org.shirdrn.activemq.component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.shirdrn.activemq.common.ActiveMQConsumer;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.conext.ContextReadable;
import org.shirdrn.activemq.conext.DefaultActiveMQContext;
import org.shirdrn.activemq.conext.PropertiesConfiguration;

public class TestDefaultActiveMQConsumer {

	private static final Log LOG = LogFactory.getLog(TestDefaultActiveMQConsumer.class);
	ActiveMQContext context;
	ActiveMQConsumer activeMQConsumer;
	
	@Before
	public void initialize() {
		ContextReadable ctx = new PropertiesConfiguration("activemq.properties");
		context = new DefaultActiveMQContext(ctx);
		activeMQConsumer = new DefaultActiveMQConsumer(context);
	}
	
	@Test
	public void pull() throws JMSException, InterruptedException {
		activeMQConsumer.establish();
		while(true) {
			Message message = activeMQConsumer.pull();
			if(message instanceof TextMessage) {
				TextMessage txt = (TextMessage) message;
				LOG.info("Pull message: " + txt.getText());
			}
			Thread.sleep(100);
		}
	}
}
