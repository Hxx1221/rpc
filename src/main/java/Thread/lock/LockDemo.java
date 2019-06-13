package Thread.lock;

import sun.misc.Unsafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockDemo {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                lock.lock();
                try {
                    Thread.sleep(10000);
                    System.out.println("==========");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
        //    thread.start();
        }
       final Unsafe unsafe = Unsafe.getUnsafe();
        boolean b = unsafe.compareAndSwapObject(new Object(), 1, new Object(), new Object());
        Condition condition = lock.newCondition();
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        StringBuffer a=new StringBuffer("");
a.append("");
        System.out.println(b);
    }
}
