package org.shirdrn.activemq.consumer;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.DefaultResult;
import org.shirdrn.activemq.common.Result;
import org.shirdrn.activemq.utils.CheckUtils;

public class SimpleConsumer<IN extends Message, OUT> extends AbstractConsumer<IN, OUT> {

	private static final Log LOG = LogFactory.getLog(SimpleConsumer.class);
	protected Session session;
	protected Destination destination;
	protected MessageConsumer messageConsumer;
	
	public SimpleConsumer(ActiveMQContext context) {
		super(context);
	}

	@Override
	public void establish() throws JMSException {
		super.establish();
		session = connectionManager.getSessionManager().getSession();
		destination = connectionManager.getSessionManager().getDestination(session);
		messageConsumer = session.createConsumer(destination);
	}

	@Override
	protected Future<Result<OUT>> submit(final IN message, final Result<OUT> result) throws Exception {
		return executor.submit(new Callable<Result<OUT>>() {

			@Override
			public Result<OUT> call() throws Exception {
				OUT outMessage = messageHandler.handle(message);
				result.setMessage(outMessage);
				return result;
			}
			
		});
	}
	
	@Override
	public Result<OUT> consume() {
		CheckUtils.checkNotNull(messageHandler, "MessageHandler is NOT set!");
		Result<OUT> result = new DefaultResult<OUT>();
		try {
			@SuppressWarnings("unchecked")
			IN jmsMessage = (IN) messageConsumer.receive();
			Future<Result<OUT>> future = submit(jmsMessage, result);
			result = future.get();
		} catch (JMSException e) {
			result.addException(e);
			LOG.error("Fail to handle message: ", e);
		} catch (InterruptedException e) {
			result.addException(e);
			LOG.error("Fail to handle message: ", e);
		} catch (ExecutionException e) {
			result.addException(e);
			LOG.error("Fail to handle message: ", e);
		} catch (Exception e) {
			result.addException(e);
			LOG.error("Fail to handle message: ", e);
		}
		return result;
	}

}
