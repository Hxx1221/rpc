package Thread.ThreadGroup;

import java.util.concurrent.TimeUnit;

public class ThreadGroupEnumerateThreadGroups {

    public static void main(String[] args) throws InterruptedException {

        ThreadGroup myGroup1 = new ThreadGroup("myGroup1");

        ThreadGroup myGroup2 = new ThreadGroup(myGroup1, "myGroup2");

        new Thread(myGroup1,()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"ss").start();

        TimeUnit.MILLISECONDS.sleep(2);

        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println(threadGroup.getName());

        ThreadGroup[] lsit=new ThreadGroup[threadGroup.activeGroupCount()];

        int enumerate = threadGroup.enumerate(lsit);
        for (ThreadGroup l:lsit
             ) {
            System.out.println(l.getName());
        }
        ThreadGroup[] lsits=new ThreadGroup[threadGroup.activeGroupCount()];
        System.out.println(enumerate);
        int enumerate1 = threadGroup.enumerate(lsits, false);

        System.out.println(enumerate1);
//        for (ThreadGroup l:lsits
//        ) {
//            System.out.println(l.getName());
//        }
      //  threadGroup.interrupt();

        myGroup1.destroy();
    }
}