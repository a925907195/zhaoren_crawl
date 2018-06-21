package com.fjsh.expression.thread;

import com.google.common.collect.Maps;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 */
public class ExecutorServicePool {

    private static Log logger = LogFactory.getLog(ExecutorServicePool.class);

    private static Map<String, ThreadPoolExecutor> executorServiceCache = Maps.newConcurrentMap();

    private static final int corePoolSize = 8;
    private static final int maxCorePoolSize = 128;
    private static final int keepAliveTime = 60;

    static {
        // 定时上报线程池状态
        Executors.newScheduledThreadPool(2).scheduleWithFixedDelay(() -> {
            int workQueueSize = 0;
            int poolSize = 0;
            int activeCount = 0;
            for (String name : executorServiceCache.keySet()) {
                ThreadPoolExecutor pool = executorServiceCache.get(name);
                workQueueSize += pool.getQueue().size();
                poolSize += pool.getPoolSize();
                activeCount += pool.getActiveCount();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    public static ExecutorService newThreadPool(String poolName) {
        ThreadPoolExecutor executorService = null;
        if (executorServiceCache.containsKey(poolName)) {
            executorService = executorServiceCache.get(poolName);
        } else {
            executorService = newThreadPool(poolName, corePoolSize, maxCorePoolSize);
            executorServiceCache.put(poolName, executorService);
        }

        return executorService;
    }

    public static ThreadPoolExecutor newThreadPool(String poolName, int corePoolSize, int maxCorePoolSize) {
        ThreadPoolExecutor pool = null;
        pool = executorServiceCache.get(poolName);
        if (pool != null) {
            return pool;
        }
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                logger.error(poolName + " discard oldest task!!");
                super.rejectedExecution(r, e);
            }
        };

        ThreadFactory threadFactory = new DefaultThreadFactory(poolName);

        pool = new ThreadPoolExecutor(corePoolSize,
                        maxCorePoolSize,
                        keepAliveTime, TimeUnit.SECONDS,
                        new LinkedBlockingQueue(Math.max(maxCorePoolSize / 4, 5)),
                        threadFactory,
               new  ThreadPoolExecutor.CallerRunsPolicy());

        executorServiceCache.put(poolName, pool);
        return pool;
    }


    private static class DefaultThreadFactory implements ThreadFactory {

        private final String namePrefix;
        private final ThreadGroup group;

        private final AtomicInteger poolNumber = new AtomicInteger(1);

        DefaultThreadFactory(String namePrefix) {
            this.namePrefix = namePrefix + "-thread-";
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + poolNumber.getAndIncrement() + "-", 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
