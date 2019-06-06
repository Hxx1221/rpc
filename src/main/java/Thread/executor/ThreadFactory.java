package Thread.executor;

public interface ThreadFactory {

    //创建线程
    Thread createThread(Runnable runnable);

}
