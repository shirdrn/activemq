package org.shirdrn.activemq.common;

import java.util.concurrent.Future;

public abstract class AbstractMessageProcessor<IN, OUT> extends AbstractClient implements Processor<IN, OUT> {

	protected MessageHandler<IN, OUT> messageHandler;
	
	public AbstractMessageProcessor(ActiveMQContext context) {
		super(context);
	}

	@Override
	public void setMessageHandler(MessageHandler<IN, OUT> messageHandler) {
		this.messageHandler = messageHandler;		
	}

	@Override
	public MessageHandler<IN, OUT> getMessageHandler() {
		return messageHandler;
	}
	
	protected abstract Future<Result<OUT>> submit(final IN message, Result<OUT> result) throws Exception;

}
