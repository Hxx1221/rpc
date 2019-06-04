package Thread.lock;

import java.util.Random;

public class TestLock {
    private final Lock lock = new BooleanLock();

    public void syncMethod() {
        try {
            lock.lock();
            Random random = new Random();
            int i = random.nextInt(10);
            System.out.println(Thread.currentThread() + "get the lock.");

            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            lock.unlock();
        }


    }


    public static void main(String[] args) {


        final TestLock testLock = new TestLock();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testLock.syncMethod();
                }
            }).start();

        }

    }
}