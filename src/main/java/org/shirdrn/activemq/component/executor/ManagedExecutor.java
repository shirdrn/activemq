package org.shirdrn.activemq.component.executor;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.shirdrn.activemq.common.ActiveMQExecutor;

public class ManagedExecutor extends ThreadPoolExecutor implements ActiveMQExecutor {

	public ManagedExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}

	@Override
	public void close() throws IOException {
		super.shutdown();
	}

}
