package Thread.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class BooleanLock implements Lock {

    //表示当前拥有锁的线程
    private Thread currentThread;
    //开关
    private boolean locked = false;
    //表示那些线程在获取当前线程时进入了阻塞状态
    private final List<Thread> blockedList = new ArrayList<Thread>();

    @Override
    public void lock() throws InterruptedException {
        synchronized (this) {
            while (locked) {

                blockedList.add(currentThread);
                this.wait();
            }
            blockedList.remove(currentThread);
            this.locked = true;
            this.currentThread = Thread.currentThread();
        }
    }

    @Override
    public void lock(long mills) throws InterruptedException, TimeoutException {
        synchronized (this) {

            if (mills <= 0) {
                this.lock();

            } else {
                long remainingMills = mills;
                long endMills = System.currentTimeMillis() + remainingMills;
                while (locked) {

                    if (remainingMills <= 0) {
                        throw new TimeoutException("can not get the lock during " + mills);
                    }
                    if (!blockedList.contains(Thread.currentThread())) {
                        blockedList.add(Thread.currentThread());
                        this.wait(remainingMills);
                        remainingMills = endMills - System.currentTimeMillis();


                    }
                    blockedList.remove(Thread.currentThread());
                    this.locked = true;
                    this.currentThread = Thread.currentThread();
                }
            }

        }
    }

    @Override
    public void unlock() {
        synchronized (this) {
            if (currentThread == Thread.currentThread()) {
                this.locked = false;
                this.notifyAll();
            }
        }

    }

    @Override
    public List<Thread> getBlockedThreads() {
        return Collections.unmodifiableList(blockedList);
    }
}