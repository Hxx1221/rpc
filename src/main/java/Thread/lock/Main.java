package Thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    private final static ReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    private final static Lock readLock=readWriteLock.readLock();
    private final static Lock writeLock=readWriteLock.writeLock();

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    readLock.lock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("==readLock.lock()==");

                try {
                    Thread.sleep(10000);
                    readLock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    readLock.lock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("==writeLock.lock()==");

                try {
                    Thread.sleep(10000);
                    readLock.unlock();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();


    }
}