package org.shirdrn.activemq.consumer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.shirdrn.activemq.common.ActiveMQContext;
import org.shirdrn.activemq.common.Result;

public class DefaultActiveMQConsumer extends AbstractSimpleActiveMQConsumer<Message> {

	private static final Log LOG = LogFactory.getLog(DefaultActiveMQConsumer.class);
	
	public DefaultActiveMQConsumer(ActiveMQContext context) {
		super(context);
	}

	@Override
	public Result<Message> consume() {
		Result<Message> result = null;
		try {
			Message message = super.messageConsumer.receive();
			Future<Result<Message>> future = super.submit(message);
			result = future.get();
		} catch (JMSException | ExecutionException | InterruptedException e) {
			LOG.error("Fail to consume message: ", e);
		}
		return result;
	}

}
