package Thread;

import java.util.concurrent.Callable;

public class SynchronizedLock{


    public synchronized void method() {
        System.out.println("method ===== synchronized");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void methodSynchronized() {
        synchronized (this) {
            System.out.println("methodSynchronized");
        }

    }


    public static  synchronized void method1() {
        System.out.println("method ===== method1");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static synchronized void method2() {
        System.out.println("method ===== method2");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void  methodSynchronizedClass() {
        synchronized (SynchronizedLock.class) {
            System.out.println("methodSynchronizedClass");
        }

    }



}