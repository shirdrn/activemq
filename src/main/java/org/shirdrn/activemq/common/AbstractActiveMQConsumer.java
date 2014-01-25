package org.shirdrn.activemq.common;

import javax.jms.MessageListener;

import org.shirdrn.activemq.utils.ReflectionUtils;


public abstract class AbstractActiveMQConsumer extends AbstractActiveMQClient implements ActiveMQConsumer {

	protected final ExecutorFactory executorFactory;
	protected final ActiveMQExecutor executor;
	protected final MessageListener messageListener;
	
	public AbstractActiveMQConsumer(ActiveMQContext context) {
		super(context);
		String listenerClass = context.get("activemq.consumer.listener.class", "org.shirdrn.activemq.DefaultMessageListener");
		messageListener = ReflectionUtils.getInstance(listenerClass, MessageListener.class, this.getClass().getClassLoader(), context);
		String listenerExecutorFactoryClass = context.get("activemq.consumer.listener.executor.factory.class", "org.shirdrn.activemq.component.executor.DefaultExecutorFactory");
		executorFactory = ReflectionUtils.getInstance(listenerExecutorFactoryClass, ExecutorFactory.class, this.getClass().getClassLoader());
		executor = executorFactory.get(context);
	}
	

}
