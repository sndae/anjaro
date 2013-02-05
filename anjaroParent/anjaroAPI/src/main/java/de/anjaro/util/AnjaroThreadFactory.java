package de.anjaro.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Simple ThreadFactory, which enables to set the thread group name.
 * 
 * Name of the Thread will be set to: pool-[poolnumber]-thread-[threadNumber]
 * 
 * @see Executors
 * 
 * @author Joachim Pasquali
 * 
 */
public class AnjaroThreadFactory implements ThreadFactory {

	static final AtomicInteger poolNumber = new AtomicInteger(1);
	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;

	/**
	 * Constructor.
	 * 
	 * @param pGroupName
	 *            Name of the group. If null, SecurityManager.getThreadGroup()
	 *            or Thread.currentThread().getThreadGroup() will be used
	 */
	public AnjaroThreadFactory(final String pGroupName) {
		if (pGroupName == null || "".equals(pGroupName.trim())) {
			final SecurityManager s = System.getSecurityManager();
			this.group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		} else {
			this.group = new ThreadGroup(pGroupName);
		}
		this.namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
	 */
	@Override
	public Thread newThread(final Runnable r) {
		final Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}

}
