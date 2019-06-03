package Thread;

public class Main {

    public static void main(String[] args) {


        final SynchronizedLock synchronizedLock = new SynchronizedLock();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedLock.method1();
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedLock.methodSynchronizedClass();
            }
        });

        thread.start();
        thread1.start();
        System.out.println("end====");
    }

}