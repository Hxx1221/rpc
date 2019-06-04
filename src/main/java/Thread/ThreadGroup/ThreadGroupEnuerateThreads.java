package Thread.ThreadGroup;

import java.util.concurrent.TimeUnit;

public class ThreadGroupEnuerateThreads {

    public static void main(String[] args) throws InterruptedException {
        ThreadGroup myGroupdd = new ThreadGroup("MyGroup");

        Thread myThread = new Thread(myGroupdd, () -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }, "MyThread");
        myThread.start();

        TimeUnit.MILLISECONDS.sleep(2);

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();


        Thread[] list = new Thread[threadGroup.activeCount()];


        int enumerate = threadGroup.enumerate(list);
        for (Thread l :
                list) {
            System.out.println(l.getName());

        }
        System.out.println(enumerate);

        int enumerate1 = threadGroup.enumerate(list, false);
        System.out.println(enumerate1);


    }

}