package org.shirdrn.activemq.component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		activeMQProducer = this.newProducer();
	}
	
	private ActiveMQProducer newProducer() {
		return new DefaultActiveMQProducer(context);
	}
	
	@Test
	public void push() throws JMSException, InterruptedException {
		activeMQProducer.establish();
		for (int i = 1000; i < 1500; i++) {
			String message = "xxxxxxxxxxx-" + i;
			activeMQProducer.push(message);
			LOG.info("Push message: " + message);
			Thread.sleep(100);
		}
	}
	
	@Test
	public void multiThreadedPush() throws JMSException, InterruptedException {
		int workers = 3;
		ActiveMQProducer[] producers = new ActiveMQProducer[workers];
		for (int i = 0; i < producers.length; i++) {
			producers[i] = newProducer();
			producers[i].establish();
		}
		ExecutorService pool = Executors.newFixedThreadPool(workers);
		
		for (int i = 0; i < 1500; i++) {
			final ActiveMQProducer producer = getProducer(i, producers);
			final String message = "xxxxxxxxxxx-" + i;
			pool.execute(new Runnable() {

				@Override
				public void run() {
					producer.push(message);
					LOG.info(Thread.currentThread().getId() + ": Push message: " + message);
				}
				
			});
		}
		synchronized(pool) {
			pool.wait();
		}
	}
	
	private ActiveMQProducer getProducer(int id, ActiveMQProducer[] producers) {
		int workers = producers.length;
		int index = id % workers;
		return producers[index];
	}
}
