package Thread.ThreadGroup;

public class TestGroup {

    public static void main(String[] args) {

        //获取当前线程的Thread
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();

        ThreadGroup group1 = new ThreadGroup("group1");

        System.out.println(threadGroup==group1.getParent());

        ThreadGroup group2 = new ThreadGroup(group1, "group2");

        System.out.println(group2.getParent()==group1);

    }


}