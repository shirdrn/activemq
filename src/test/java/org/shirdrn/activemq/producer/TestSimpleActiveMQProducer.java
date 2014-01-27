package org.shirdrn.activemq.producer;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.ActiveMQProducer;
import org.shirdrn.activemq.common.MessageHandler;
import org.shirdrn.activemq.conext.ContextReadable;
import org.shirdrn.activemq.conext.DefaultActiveMQContext;
import org.shirdrn.activemq.conext.PropertiesConfiguration;

public class TestSimpleActiveMQProducer {

	private static final Log LOG = LogFactory.getLog(TestSimpleActiveMQProducer.class);
	ActiveMQContext context;
	ActiveMQProducer<String, TextMessage> producer;
	
	@Before
	public void initialize() {
		ContextReadable ctx = new PropertiesConfiguration("activemq.properties");
		context = new DefaultActiveMQContext(ctx);
		producer = this.newProducer();
	}
	
	private ActiveMQProducer<String, TextMessage> newProducer() {
		return new SimpleActiveMQProducer<String, TextMessage>(context);
	}
	
	private final AtomicInteger counter = new AtomicInteger(0);
	
	@Test
	public void produce() throws JMSException, InterruptedException, IOException {
		producer.establish();
		producer.setMessageHandler(new MessageHandler<String, TextMessage>() {
			@Override
			public TextMessage handle(String message) throws JMSException {
				LOG.info("Produce message: " + message);
				TextMessage jmsMessage = new ActiveMQTextMessage();
				jmsMessage.setText(message);
				counter.incrementAndGet();
				return jmsMessage;
			}
		});
		
		// produce messages
		int total = 100;
		for (int i = 0; i < total; i++) {
			String message = "xxxxxxxxxxx-" + i;
			producer.produce(message);
		}
		while(counter.get() < total) {
			Thread.sleep(500);
		}
		producer.close();
	}
	
}
