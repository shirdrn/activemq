package org.shirdrn.activemq.producer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.JMSException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.ActiveMQProducer;
import org.shirdrn.activemq.common.MessageHandleException;
import org.shirdrn.activemq.common.MessageHandler;
import org.shirdrn.activemq.conext.ContextReadable;
import org.shirdrn.activemq.conext.DefaultActiveMQContext;
import org.shirdrn.activemq.conext.PropertiesConfiguration;
import org.shirdrn.activemq.producer.ActiveMQTextProducer;

public class TestActiveMQTextProducer {

	private static final Log LOG = LogFactory.getLog(TestActiveMQTextProducer.class);
	ActiveMQContext context;
	ActiveMQProducer<String> producer;
	
	@Before
	public void initialize() {
		ContextReadable ctx = new PropertiesConfiguration("activemq.properties");
		context = new DefaultActiveMQContext(ctx);
		producer = this.newProducer();
	}
	
	private ActiveMQProducer<String> newProducer() {
		return new ActiveMQTextProducer(context);
	}
	
	@Test
	public void produce() throws JMSException, InterruptedException, IOException {
		producer.establish();
		producer.setMessageHandler(new MessageHandler<String>() {
			@Override
			public void handle(String message) throws MessageHandleException {
				LOG.info("Produce message: " + message);
			}
		});
		for (int i = 1000; i < 1200; i++) {
			String message = "xxxxxxxxxxx-" + i;
			producer.produce(message);
		}
		producer.close();
	}
	
	private AtomicInteger counter = new AtomicInteger(0);
	
	@Test
	public void multiThreadedProduce() throws JMSException, InterruptedException, IOException {
		int total = 300;
		int workers = 3;
		@SuppressWarnings("unchecked")
		ActiveMQProducer<String>[] producers = new ActiveMQProducer[workers];
		for (int i = 0; i < producers.length; i++) {
			producers[i] = newProducer();
			producers[i].establish();
		}
		ExecutorService pool = Executors.newFixedThreadPool(workers);
		
		for (int i = 0; i < total; i++) {
			final ActiveMQProducer<String> producer = getProducer(i, producers);
			final String message = "xxxxxxxxxxx-" + i;
			pool.execute(new Runnable() {

				@Override
				public void run() {
					producer.produce(message);
					LOG.info(Thread.currentThread().getId() + ": Produce message: " + message);
					counter.incrementAndGet();
				}
				
			});
		}
		
		while(counter.get() < total);
		
		for (int i = 0; i < producers.length; i++) {
			producers[i].close();
		}
	}
	
	private ActiveMQProducer<String> getProducer(int id, ActiveMQProducer<String>[] producers) {
		int workers = producers.length;
		int index = id % workers;
		return producers[index];
	}
}
