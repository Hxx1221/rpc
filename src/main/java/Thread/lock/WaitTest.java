package Thread.lock;

import java.util.concurrent.CyclicBarrier;

public class WaitTest {
    private static Object obj = new Object();
private static CyclicBarrier cyclicBarrier=new CyclicBarrier(2);
    public static void main(String[] args) throws InterruptedException {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("=====await1000=========");
            }
        });
        thread.start();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("=======await500=======");
            }
        });
        thread1.start();

    }


}