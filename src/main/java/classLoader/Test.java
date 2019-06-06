package classLoader;

public class Test {

    private volatile  static int a = 0;

    public static void main(String[] args) {
        ClassLoader classLoader = new Test().getClass().getClassLoader();
        System.out.println(classLoader.getParent());
        int i = 0;
        for (i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    a++;
                    System.out.println(a);
                }
            }).start();
        }
    }
}