package Thread.executor;

public interface RunnableQueue {
    //将任务提交到队列
    void offer(Runnable runnable);

    //工作线程通过take方法获取runnable
    Runnable take();

    //获取任务队列中任务的数量
    int size();
}