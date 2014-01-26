package org.shirdrn.activemq.consumer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.Result;

public class ActiveMQTextConsumer extends AbstractSimpleActiveMQConsumer<String> {

	private static final Log LOG = LogFactory.getLog(ActiveMQTextConsumer.class);
	
	public ActiveMQTextConsumer(ActiveMQContext context) {
		super(context);
	}

	@Override
	public Result<String> consume() {
		Result<String> result = null;
		Message message = null;
		TextMessage m = null;
		try {
			message = messageConsumer.receive();
			if(message != null && message instanceof TextMessage) {
				m = (TextMessage) message;
				String text = m.getText();
				Future<Result<String>> future = super.submit(text);	
				result = future.get();
			}
		} catch (JMSException | ExecutionException | InterruptedException e) {
			LOG.error("Fail to consume message: ", e);
		}
		return result;
	}


}
