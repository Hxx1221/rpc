package Thread.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSpportDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("threadName:" + Thread.currentThread().getName() + "===park");
                LockSupport.park();
                System.out.println("threadName:" + Thread.currentThread().getName() + "===park-end");

            }
        });

        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("threadName:" + Thread.currentThread().getName() + "===unpark");

                LockSupport.unpark(thread);
                System.out.println("threadName:" + Thread.currentThread().getName() + "===unpark-end");

            }
        });
        thread1.start();

    }
}