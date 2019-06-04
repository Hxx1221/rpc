package Thread.lock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class BooleanLock implements Lock {

    //��ʾ��ǰӵ�������߳�
    private Thread currentThread;
    //����
    private boolean locked = false;
    //��ʾ��Щ�߳��ڻ�ȡ��ǰ�߳�ʱ����������״̬
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