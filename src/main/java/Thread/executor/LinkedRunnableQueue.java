package Thread.executor;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue {

    //任务队列的最大的容量
    private final int limit;

    //若任务队列中的任务已经满了，则需要执行拒绝策略
    private final DenyPolicy denyPolicy;
    //存放任务的队列
    private final LinkedList<Runnable> runnableList = new LinkedList<>();

    private final ThreadPool threadPool;

    public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.limit = limit;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }

    @Override
    public void offer(Runnable runnable) {

        synchronized (runnableList) {
            //任务队列中满了 就使用拒绝策略
            if (runnableList.size() >= limit) {
                denyPolicy.reject(runnable, threadPool);
            } else {
                //添加任务到队列中，并且唤醒阻塞中的线程
                runnableList.addLast(runnable);
                runnableList.notifyAll();
            }
        }
    }

    @Override
    public Runnable take() {
        synchronized (runnableList) {

            //队列中如果是空的 那么就挂起 等待唤醒
            while (runnableList.isEmpty()) {

                try {
                    runnableList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        //不为空就取出第一个任务
        return runnableList.removeFirst();
    }

    @Override
    public int size() {
        synchronized (runnableList) {
            //当前任务中的任务数量
            return runnableList.size();
        }
    }
}