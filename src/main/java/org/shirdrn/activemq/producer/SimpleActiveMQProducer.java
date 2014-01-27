package org.shirdrn.activemq.producer;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.DefaultResult;
import org.shirdrn.activemq.common.Result;

public class SimpleActiveMQProducer<IN, OUT extends Message> extends AbstractActiveMQProducer<IN, OUT> {

	private static final Log LOG = LogFactory.getLog(SimpleActiveMQProducer.class);
	protected Session session;
	protected MessageProducer messageProducer;
	private Destination destination;
	
	public SimpleActiveMQProducer(ActiveMQContext context) {
		super(context);
	}
	
	@Override
	public void establish() throws JMSException {
		super.establish();
		session = connectionManager.getSessionManager().getSession();
		destination = connectionManager.getSessionManager().getDestination(session);
		messageProducer = session.createProducer(destination);
		int deliveryMode = context.getConfig().getInt("activemq.delivery.mode", DeliveryMode.NON_PERSISTENT);
		messageProducer.setDeliveryMode(deliveryMode);
		LOG.info("Config: deliveryMode = " + deliveryMode);
	}

	@Override
	protected Future<Result<OUT>> submit(final IN message, final Result<OUT> result) throws Exception {
		return executor.submit(new Callable<Result<OUT>>() {

			@Override
			public Result<OUT> call() throws Exception {
				OUT jmsMessage = messageHandler.handle(message);
				result.setMessage(jmsMessage);
				messageProducer.send(jmsMessage);
				return result;
			}
			
		});
	}

	@Override
	public Result<OUT> produce(IN message) {
		checkMessageHandler();
		Result<OUT> result = new DefaultResult<OUT>();
		try {
			Future<Result<OUT>> future = this.submit(message, result);
			result = future.get();
		} catch (JMSException e) {
			result.addException(e);
			LOG.error("Fail to handle message: " + e);
		} catch (InterruptedException e) {
			result.addException(e);
			LOG.error("Fail to handle message: " + e);
		} catch (ExecutionException e) {
			result.addException(e);
			LOG.error("Fail to handle message: " + e);
		} catch (Exception e) {
			result.addException(e);
			LOG.error("Fail to handle message: " + e);
		}
		return result;
	}
	
	private void checkMessageHandler() {
		if(messageHandler == null) {
			throw new RuntimeException("MessageHandler is NOT set!");
		}		
	}
	
}
