package org.shirdrn.activemq.component.executor;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.shirdrn.activemq.common.ActiveMQExecutor;
import org.shirdrn.activemq.common.ExecutorFactory;
import org.shirdrn.activemq.conext.ContextReadable;

public class DefaultExecutorFactory extends ExecutorFactory {

	public DefaultExecutorFactory() {
		super();
	}

	@Override
	public ActiveMQExecutor get(ContextReadable key) {
		ActiveMQExecutor pool = super.get(key);
		if (pool == null) {
			pool = createAndCachePool(key);
		} else {
			if (pool.isShutdown() || pool.isTerminated()) {
				pool = createAndCachePool(key);
			}
		}
		return pool;
	}

	private ActiveMQExecutor createAndCachePool(ContextReadable key) {
		ActiveMQExecutor pool = null;
		String name = key.get("activemq.executor.name", "ACTIVEMQ");
		int nThreads = key.getInt("activemq.executor.worker.count", 1);
		int workQSize = 2 * nThreads;
		BlockingQueue<Runnable> q = new ArrayBlockingQueue<Runnable>(workQSize);
		pool = new ManagedExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, q, new NamedThreadFactory(name), new ScheduleAgainPolicy(workQSize));
		super.put(key, pool);
		return pool;
	}

	@Override
	public void closeAll() {
		Iterator<Entry<ContextReadable, ActiveMQExecutor>> iter = super.iterator();
		while (iter.hasNext()) {
			ActiveMQExecutor pool = iter.next().getValue();
			if (!pool.isShutdown()) {
				pool.shutdown();
			}
		}
	}

	@Override
	public void close(ActiveMQExecutor value) {
		if (!value.isShutdown()) {
			value.shutdown();
		}
	}
}