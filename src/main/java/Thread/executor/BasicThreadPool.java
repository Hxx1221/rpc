package Thread.executor;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BasicThreadPool extends Thread implements ThreadPool {

    //初始化线程数量
    private final int initSize;

    //线程池中最大线程数量
    private final int maxSize;
    //线程池核心线程数量
    private final int coreSize;
    //当前活跃的线程数量
    private int activeCount;
    //创建线程的工厂
    private final ThreadFactory threadFactory;
    //任务队列
    private final RunnableQueue runnableQueue;
    //线程池是否已经被关闭
    private volatile boolean isShutdown = false;
    //工作线程队列
    private final Queue<ThreadTask> taskQueue = new ArrayDeque<ThreadTask>();

    //任务过多的时候采用的策略
    private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();

    private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();


    private final long keepAliveTime;

    private final TimeUnit timeUnit;

    //构造函数   初始化线程数量   最大的线程数量   核心线程数量  任务队列的最大数量
    public BasicThreadPool(int initSize, int maxSize, int coreSize, int queueSize) {

        this(initSize, maxSize, coreSize, DEFAULT_THREAD_FACTORY, queueSize, DEFAULT_DENY_POLICY, 10, TimeUnit.SECONDS);
    }

    public BasicThreadPool(int initSize, int maxSize, int coreSize, ThreadFactory threadFactory, int queueSize
            , DenyPolicy denyPolicy, long keepAliveTime, TimeUnit timeUnit) {

        this.initSize = initSize;
        this.maxSize = maxSize;
        this.coreSize = coreSize;
        this.threadFactory = threadFactory;
        this.runnableQueue = new LinkedRunnableQueue(queueSize, denyPolicy, this);
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.init();
    }

    private void init() {

        start();

        for (int i = 0; i < initSize; i++) {
            newThread();
        }
    }

    private void newThread() {
        InternalTask internalTask = new InternalTask(runnableQueue);
        Thread thread = this.threadFactory.createThread(internalTask);
        ThreadTask threadTask = new ThreadTask(thread, internalTask);
        taskQueue.offer(threadTask);
        this.activeCount++;
        thread.start();
    }
    @Override
    public void run() {


        while (!isShutdown && !isInterrupted()) {
            try {
                timeUnit.sleep(keepAliveTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                isShutdown = true;
                break;
            }

            synchronized (this) {
                if (isShutdown) {
                    break;
                }
                //当前的队列中有任务尚未处理  并且activeCount小于coreSize则继续扩容
                if (runnableQueue.size() > 0 && activeCount < coreSize) {
                    for (int i = initSize; i < coreSize; i++) {
                        newThread();
                    }
                    continue;
                }
                //当前的队列中有任务尚未处理  并且activeCount小于maxSize则继续扩容
                if (runnableQueue.size() > 0 && activeCount < maxSize) {

                    for (int i = coreSize; i < maxSize; i++) {
                        newThread();
                    }
                }
                //当前的队列中没有任务  并且activeCount大于coreSize则进行回收
                if (runnableQueue.size() == 0 && activeCount > coreSize) {

                    for (int i = coreSize; i < activeCount; i++) {

                        removeThread();
                    }
                }
            }
        }
    }


    @Override
    public void execute(Runnable runnable) {

        if (this.isShutdown) {
            throw new IllegalStateException("The thread is destroy");
        }
        //插入任务
        this.runnableQueue.offer(runnable);
    }

    @Override
    public void shutdown() {

        synchronized (this) {
            if (isShutdown) {
                return;
            }
            isShutdown = true;
            for (ThreadTask threadTask : taskQueue) {
                threadTask.internalTask.stop();
                threadTask.thread.interrupt();
            }
            this.interrupt();
        }


    }

    @Override
    public int getInitSize() {
        if (isShutdown) {

            throw new IllegalStateException("The thread pool is destroy");
        }


        return this.initSize;
    }

    @Override
    public int getMaxSize() {
        if (isShutdown) {

            throw new IllegalStateException("The thread pool is destroy");
        }

        return this.maxSize;
    }

    @Override
    public int getCoreSize() {
        if (isShutdown) {

            throw new IllegalStateException("The thread pool is destroy");
        }

        return this.coreSize;
    }

    @Override
    public int getQueueSize() {
        if (isShutdown) {

            throw new IllegalStateException("The thread pool is destroy");
        }

        return this.runnableQueue.size();
    }

    @Override
    public int getActiveCount() {
        synchronized (this) {

            return this.activeCount;
        }
    }

    @Override
    public boolean isShutdown() {

        return this.isShutdown;

    }

    private void removeThread() {
        ThreadTask remove = taskQueue.remove();
        remove.internalTask.stop();
        this.activeCount--;


    }


    public static class ThreadTask {
        Thread thread;
        InternalTask internalTask;

        public ThreadTask(Thread thread, InternalTask internalTask) {
            this.thread = thread;
            this.internalTask = internalTask;
        }
    }

    public static class DefaultThreadFactory implements ThreadFactory {

        private static final AtomicInteger GROUP_COUNT = new AtomicInteger(1);

        private static final ThreadGroup group = new ThreadGroup("MyThreadPool-" + GROUP_COUNT.getAndDecrement());
        private static final AtomicInteger COUNTER = new AtomicInteger(0);


        @Override
        public Thread createThread(Runnable runnable) {
            return new Thread(group, runnable, "thread-pool-" + COUNTER.getAndDecrement());
        }
    }
}