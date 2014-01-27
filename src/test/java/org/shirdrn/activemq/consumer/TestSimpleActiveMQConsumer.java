package org.shirdrn.activemq.consumer;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.shirdrn.activemq.common.ActiveMQConsumer;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.MessageHandler;
import org.shirdrn.activemq.common.Result;
import org.shirdrn.activemq.conext.ContextReadable;
import org.shirdrn.activemq.conext.DefaultActiveMQContext;
import org.shirdrn.activemq.conext.PropertiesConfiguration;

public class TestSimpleActiveMQConsumer {

	private static final Log LOG = LogFactory.getLog(TestSimpleActiveMQConsumer.class);
	ActiveMQContext context;
	ActiveMQConsumer<TextMessage, String> consumer;
	
	@Before
	public void initialize() {
		ContextReadable ctx = new PropertiesConfiguration("activemq.properties");
		context = new DefaultActiveMQContext(ctx);
		consumer = new SimpleActiveMQConsumer<TextMessage, String>(context);
	}
	
	@Test
	public void consume() throws JMSException, InterruptedException {
		consumer.establish();
		consumer.setMessageHandler(new MessageHandler<TextMessage, String>() {

			@Override
			public String handle(TextMessage message) throws JMSException {
				return message.getText();
			}
			
		});
		while(true) {
			Result<String> result = consumer.consume();
			LOG.info("Consume message: " + result.getMessage());
		}
//		consumer.close();
	}
}
