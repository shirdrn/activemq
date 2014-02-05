package org.shirdrn.activemq.consumer;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.shirdrn.activemq.common.Consumer;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.MessageHandler;
import org.shirdrn.activemq.common.Result;
import org.shirdrn.activemq.conext.ContextReadable;
import org.shirdrn.activemq.conext.DefaultContext;
import org.shirdrn.activemq.conext.PropertiesConfiguration;

public class TestSimpleTopicConsumer {

	private static final Log LOG = LogFactory.getLog(TestSimpleTopicConsumer.class);
	ActiveMQContext context;
	Consumer<TextMessage, String> consumer;
	
	@Before
	public void initialize() {
		ContextReadable ctx = new PropertiesConfiguration("activemq-topic.properties");
		context = new DefaultContext(ctx);
		consumer = new SimpleConsumer<TextMessage, String>(context);
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
