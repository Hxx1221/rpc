package Thread;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author:He_xixiang
 * @Title: ${enclosing_method}
 * @Description: ${todo}(这里用一句话描述这个方法的作用)
 * @return ${return_type}    返回类型
 * @throws
 */
public class Test {
    private static List<String> paths = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int a=0;
               while (true){
                   System.out.println(a++);
               }
            }
        });
        thread.setPriority(10);
        thread.start();

        System.out.println(thread.getPriority());
        final ClassLoader contextClassLoader = thread.getContextClassLoader();
        System.out.println(contextClassLoader.getClass().getName());
        thread.interrupt();
        final boolean interrupted = thread.isInterrupted();
        System.out.println(interrupted+"+++++++++++++++++++++++++++++");
        System.out.println(thread.isInterrupted()+"+++++++++++++++++++++++++++++");
        thread.isInterrupted();
        thread.join();
        System.out.println(111111);


    }


}
